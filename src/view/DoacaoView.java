package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Doador;
import model.Usuario;
import service.PetService;
import service.UsuarioService;

public class DoacaoView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();
    private final PetService petService = new PetService();

    public DoacaoView() {
        Usuario usuario = usuarioService.getUsuarioLogado();

        setTitle("ViraLar - Cadastro de Pet para Doação");
        setSize(920, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(214, 226, 243));

        JButton voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(e -> {
            new HomeView();
            dispose();
        });

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        top.add(voltarBtn, BorderLayout.WEST);
        top.add(new JLabel("Cadastre um Pet para Adoção"), BorderLayout.CENTER);

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
        form.add(historia);

        JButton cadastrarBtn = new JButton("Cadastrar pet");

        cadastrarBtn.addActionListener(e -> {
            try {
                if (!(usuario instanceof Doador doador)) {
                    throw new IllegalArgumentException("Apenas usuários do tipo Doador podem cadastrar pets.");
                }

                int idade = Integer.parseInt(idadePet.getText().trim());

                petService.cadastrarPet(
                        nomePet.getText().trim(),
                        tipoPet.getText().trim(),
                        idade,
                        portePet.getText().trim(),
                        personalidade.getText().trim(),
                        historia.getText().trim(),
                        localizacao.getText().trim(),
                        doador
                );

                JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso.");
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
        bottom.add(cadastrarBtn);

        root.add(top, BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        setVisible(true);
    }
}