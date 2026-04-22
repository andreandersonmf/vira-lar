package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
        root.setBackground(new Color(214, 226, 243));

        JPanel loginPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        loginPanel.setBackground(new Color(214, 226, 243));
        loginPanel.setBorder(BorderFactory.createTitledBorder("Bem-vindo de volta!"));

        JButton googleBtn = new JButton("Login com Google");
        JButton instagramBtn = new JButton("Login com Instagram");
        JButton facebookBtn = new JButton("Login com Facebook");
        googleBtn.setEnabled(false);
        instagramBtn.setEnabled(false);
        facebookBtn.setEnabled(false);

        JTextField loginEmail = new JTextField();
        JPasswordField loginSenha = new JPasswordField();
        JButton entrarBtn = new JButton("Entrar");
        JButton voltarBtn = new JButton("Voltar");

        loginPanel.add(googleBtn);
        loginPanel.add(instagramBtn);
        loginPanel.add(facebookBtn);
        loginPanel.add(new JLabel("OU", SwingConstants.CENTER));
        loginPanel.add(new JLabel("E-mail"));
        loginPanel.add(loginEmail);
        loginPanel.add(new JLabel("Senha"));
        loginPanel.add(loginSenha);
        loginPanel.add(entrarBtn);
        loginPanel.add(voltarBtn);

        JPanel cadastroPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        cadastroPanel.setBackground(new Color(226, 187, 132));
        cadastroPanel.setBorder(BorderFactory.createTitledBorder("Cadastre-se aqui"));

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telefoneField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Adotante", "Doador"});
        JButton cadastrarBtn = new JButton("Cadastrar");

        cadastroPanel.add(new JLabel("Nome completo"));
        cadastroPanel.add(nomeField);
        cadastroPanel.add(new JLabel("Celular ou e-mail"));
        cadastroPanel.add(emailField);
        cadastroPanel.add(new JLabel("Telefone"));
        cadastroPanel.add(telefoneField);
        cadastroPanel.add(new JLabel("Senha"));
        cadastroPanel.add(senhaField);
        cadastroPanel.add(new JLabel("Tipo de usuário"));
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
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setForeground(new Color(35, 91, 174));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(root, BorderLayout.CENTER);

        root.add(loginPanel);
        root.add(cadastroPanel);

        setVisible(true);
    }
}