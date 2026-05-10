package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Doador;
import model.Pet;
import model.Usuario;
import service.PetService;
import service.UsuarioService;

public class DoacaoView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();
    private final PetService petService = new PetService();

    public DoacaoView() {
        this(null);
    }

    public DoacaoView(Pet petParaEditar) {
        Usuario usuario = usuarioService.getUsuarioLogado();
        boolean modoEdicao = petParaEditar != null;

        setTitle(modoEdicao ? "ViraLar - Editar Pet" : "ViraLar - Cadastro de Pet para Doação");
        setSize(920, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(214, 226, 243));

        JButton voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(e -> {
            new PerfilView();
            dispose();
        });

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        top.add(voltarBtn, BorderLayout.WEST);
        top.add(new JLabel(modoEdicao ? "Editar Pet" : "Cadastre um Pet para Adoção"), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 12, 12));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nomePet = new JTextField();
        JTextField tipoPet = new JTextField();
        JTextField idadePet = new JTextField();
        JTextField portePet = new JTextField();
        JTextField localizacao = new JTextField();
        JTextField personalidade = new JTextField();
        JTextArea historia = new JTextArea(5, 20);

        final String[] imagemPath = {null};

        JLabel imagemPreview = new JLabel("Nenhuma imagem selecionada");
        imagemPreview.setHorizontalAlignment(JLabel.CENTER);

        if (modoEdicao) {
            nomePet.setText(petParaEditar.getNome());
            tipoPet.setText(petParaEditar.getEspecie());
            idadePet.setText(String.valueOf(petParaEditar.getIdade()));
            portePet.setText(petParaEditar.getPorte());
            localizacao.setText(petParaEditar.getLocalizacao());
            personalidade.setText(petParaEditar.getPersonalidade());
            historia.setText(petParaEditar.getHistoria());
            imagemPath[0] = petParaEditar.getImagemPath();
            atualizarPreview(imagemPreview, imagemPath[0]);
        }

        JButton escolherImagemBtn = new JButton("Escolher imagem do pet");

        form.add(new JLabel("Nome do pet"));
        form.add(nomePet);
        form.add(new JLabel("Tipo"));
        form.add(tipoPet);
        form.add(new JLabel("Idade"));
        form.add(idadePet);
        form.add(new JLabel("Porte"));
        form.add(portePet);
        form.add(new JLabel("Localização"));
        form.add(localizacao);
        form.add(new JLabel("Personalidade"));
        form.add(personalidade);
        form.add(new JLabel("História do pet"));
        form.add(new JScrollPane(historia));
        form.add(new JLabel("Imagem do pet"));
        form.add(escolherImagemBtn);

        form.add(new JLabel(""));
        form.add(imagemPreview);

        JButton salvarBtn = new JButton(modoEdicao ? "Salvar alterações" : "Cadastrar pet");

        escolherImagemBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int resultado = fileChooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();
                imagemPath[0] = arquivo.getAbsolutePath();
                atualizarPreview(imagemPreview, imagemPath[0]);
            }
        });

        salvarBtn.addActionListener(e -> {
            try {
                if (!(usuario instanceof Doador doador)) {
                    throw new IllegalArgumentException("Apenas usuários do tipo Doador podem cadastrar ou editar pets.");
                }

                int idade = Integer.parseInt(idadePet.getText().trim());

                if (modoEdicao) {
                    petService.editarPet(
                            petParaEditar,
                            doador,
                            nomePet.getText().trim(),
                            tipoPet.getText().trim(),
                            idade,
                            portePet.getText().trim(),
                            personalidade.getText().trim(),
                            historia.getText().trim(),
                            localizacao.getText().trim(),
                            imagemPath[0]
                    );
                    JOptionPane.showMessageDialog(this, "Pet atualizado com sucesso.");
                } else {
                    petService.cadastrarPet(
                            nomePet.getText().trim(),
                            tipoPet.getText().trim(),
                            idade,
                            portePet.getText().trim(),
                            personalidade.getText().trim(),
                            historia.getText().trim(),
                            localizacao.getText().trim(),
                            doador,
                            imagemPath[0]
                    );
                    JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso.");
                }

                new PerfilView();
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "A idade deve ser um número inteiro.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(214, 226, 243));
        bottom.add(salvarBtn);

        root.add(top, BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        setVisible(true);
    }

    private void atualizarPreview(JLabel imagemPreview, String caminhoImagem) {
        imagemPreview.setIcon(null);

        if (caminhoImagem == null || caminhoImagem.isBlank()) {
            imagemPreview.setText("Nenhuma imagem selecionada");
            return;
        }

        try {
            ImageIcon icon;
            if (caminhoImagem.startsWith("/resources/")) {
                icon = new ImageIcon(getClass().getResource(caminhoImagem));
            } else {
                icon = new ImageIcon(caminhoImagem);
            }

            if (icon.getIconWidth() <= 0) {
                imagemPreview.setText("Imagem não encontrada");
                return;
            }

            Image img = icon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
            imagemPreview.setIcon(new ImageIcon(img));
            imagemPreview.setText("");
        } catch (Exception ex) {
            imagemPreview.setText("Imagem não encontrada");
        }
    }
}
