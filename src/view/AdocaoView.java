package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Adotante;
import model.Pet;
import model.Usuario;
import service.AdocaoService;
import service.UsuarioService;

public class AdocaoView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();
    private final AdocaoService adocaoService = new AdocaoService();

    public AdocaoView(Pet petSelecionado) {
        Usuario usuario = usuarioService.getUsuarioLogado();

        setTitle("ViraLar - Solicitação de Adoção");
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
        top.add(new JLabel("Formulário de Adoção Responsável"), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 12, 12));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField petField = new JTextField();
        petField.setEditable(false);
        petField.setText(petSelecionado != null ? petSelecionado.getNome() : "Selecione um pet pela tela de detalhes");

        JTextField nomeCompleto = new JTextField();
        JTextField idade = new JTextField();
        JTextField telefone = new JTextField();
        JTextField endereco = new JTextField();
        JComboBox<String> moraEm = new JComboBox<>(new String[]{"Casa", "Apartamento"});
        JComboBox<String> outrosAnimais = new JComboBox<>(new String[]{"Não", "Sim"});
        JComboBox<String> jaTevePets = new JComboBox<>(new String[]{"Não", "Sim"});
        JTextArea motivo = new JTextArea(5, 20);

        form.add(new JLabel("Pet selecionado"));
        form.add(petField);
        form.add(new JLabel("Nome completo"));
        form.add(nomeCompleto);
        form.add(new JLabel("Idade"));
        form.add(idade);
        form.add(new JLabel("Telefone"));
        form.add(telefone);
        form.add(new JLabel("Endereço completo"));
        form.add(endereco);
        form.add(new JLabel("Mora em"));
        form.add(moraEm);
        form.add(new JLabel("Tem outros animais?"));
        form.add(outrosAnimais);
        form.add(new JLabel("Já teve pets antes?"));
        form.add(jaTevePets);
        form.add(new JLabel("Por que você quer adotar?"));
        form.add(motivo);

        JButton enviarBtn = new JButton("Enviar solicitação");

        enviarBtn.addActionListener(e -> {
            try {
                if (!(usuario instanceof Adotante adotante)) {
                    throw new IllegalArgumentException("Apenas usuários do tipo Adotante podem solicitar adoção.");
                }
                if (petSelecionado == null) {
                    throw new IllegalArgumentException("Abra primeiro os detalhes de um pet para solicitar adoção.");
                }

                adocaoService.solicitarAdocao(
                        petSelecionado,
                        adotante,
                        nomeCompleto.getText().trim(),
                        idade.getText().trim(),
                        telefone.getText().trim(),
                        endereco.getText().trim(),
                        (String) moraEm.getSelectedItem(),
                        (String) outrosAnimais.getSelectedItem(),
                        (String) jaTevePets.getSelectedItem(),
                        motivo.getText().trim()
                );

                JOptionPane.showMessageDialog(this, "Solicitação enviada com sucesso.");
                new PerfilView();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(214, 226, 243));
        bottom.add(enviarBtn);

        root.add(top, BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        setVisible(true);
    }
}