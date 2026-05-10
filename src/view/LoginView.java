package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import service.UsuarioService;

public class LoginView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();

    public LoginView() {
        setTitle("ViraLar - Login e Cadastro");
        setSize(960, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridLayout(1, 2, 20, 0));
        root.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        root.setBackground(Theme.BACKGROUND);

        JPanel loginPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        loginPanel.setBackground(Theme.CARD);
        loginPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.PRIMARY),
                "Bem-vindo de volta!",
                0,
                0,
                Theme.SUBTITLE,
                Theme.TEXT
        ));


        JTextField loginEmail = new JTextField();
        JPasswordField loginSenha = new JPasswordField();
        JButton entrarBtn = new JButton("Entrar");
        JButton voltarBtn = new JButton("Voltar");
        Theme.primaryButton(entrarBtn);
        Theme.secondaryButton(voltarBtn);

        JLabel loginEmailLabel = new JLabel("E-mail");
        JLabel loginSenhaLabel = new JLabel("Senha");

        loginEmailLabel.setForeground(Theme.TEXT);
        loginSenhaLabel.setForeground(Theme.TEXT);

        loginPanel.add(loginEmailLabel);
        loginPanel.add(loginEmail);
        loginPanel.add(loginSenhaLabel);
        loginPanel.add(loginSenha);
        loginPanel.add(entrarBtn);
        loginPanel.add(voltarBtn);

        JPanel cadastroPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        cadastroPanel.setBackground(Theme.CARD);
        cadastroPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.PRIMARY),
                "Cadastre-se aqui",
                0,
                0,
                Theme.SUBTITLE,
                Theme.TEXT
        ));

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telefoneField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Adotante", "Doador"});
        JButton cadastrarBtn = new JButton("Cadastrar");
        Theme.primaryButton(cadastrarBtn);

        JLabel nomeLabel = new JLabel("Nome completo");
        JLabel emailCadastroLabel = new JLabel("E-mail");
        JLabel telefoneLabel = new JLabel("Celular");
        JLabel senhaCadastroLabel = new JLabel("Senha");
        JLabel tipoLabel = new JLabel("Tipo de usuário");

        nomeLabel.setForeground(Theme.TEXT);
        emailCadastroLabel.setForeground(Theme.TEXT);
        telefoneLabel.setForeground(Theme.TEXT);
        senhaCadastroLabel.setForeground(Theme.TEXT);
        tipoLabel.setForeground(Theme.TEXT);

        cadastroPanel.add(nomeLabel);
        cadastroPanel.add(nomeField);
        cadastroPanel.add(emailCadastroLabel);
        cadastroPanel.add(emailField);
        cadastroPanel.add(telefoneLabel);
        cadastroPanel.add(telefoneField);
        cadastroPanel.add(senhaCadastroLabel);
        cadastroPanel.add(senhaField);
        cadastroPanel.add(tipoLabel);
        cadastroPanel.add(tipoCombo);
        cadastroPanel.add(cadastrarBtn);

        entrarBtn.addActionListener(e -> {
            try {
                String email = loginEmail.getText().trim();
                String senha = new String(loginSenha.getPassword());

                if (usuarioService.login(email, senha) != null) {
                    JOptionPane.showMessageDialog(this, "Login realizado com sucesso.");
                    new HomeView();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        cadastrarBtn.addActionListener(e -> {
            try {
                usuarioService.cadastrarUsuario(
                        nomeField.getText().trim(),
                        emailField.getText().trim(),
                        new String(senhaField.getPassword()),
                        telefoneField.getText().trim(),
                        (String) tipoCombo.getSelectedItem()
                );

                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso.");
                new HomeView();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        voltarBtn.addActionListener(e -> {
            new HomeView();
            dispose();
        });

        JLabel header = new JLabel("VIRALAR - toda patinha merece um lar!", SwingConstants.CENTER);
        header.setFont(Theme.SUBTITLE);
        header.setForeground(Theme.PRIMARY);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(root, BorderLayout.CENTER);

        root.add(loginPanel);
        root.add(cadastroPanel);

        getContentPane().setBackground(Theme.BACKGROUND);
        setVisible(true);
    }
}