package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Pet;
import service.UsuarioService;

public class DetalhePetView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();

    public DetalhePetView(Pet pet) {
        setTitle("ViraLar - Detalhes do Pet");
        setSize(950, 650);
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

        JPanel center = new JPanel(new GridLayout(1, 3, 20, 20));
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel imagem = new JLabel(pet.getEspecie().equalsIgnoreCase("Gato") ? "🐱" : "🐶", JLabel.CENTER);
        imagem.setFont(new Font("Arial", Font.PLAIN, 130));
        imagem.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel infoPrincipal = new JPanel(new GridLayout(0, 1, 8, 8));
        infoPrincipal.setBackground(Color.WHITE);
        infoPrincipal.add(criarTitulo(pet.getNome()));
        infoPrincipal.add(new JLabel(pet.getIdade() + " ano(s) - " + pet.getEspecie() + " - " + pet.getPorte()));
        infoPrincipal.add(new JLabel("Personalidade: " + pet.getPersonalidade()));
        infoPrincipal.add(new JLabel("Localização: " + pet.getLocalizacao()));
        infoPrincipal.add(new JLabel("Contato do doador: " + pet.getDoador().getTelefone()));
        infoPrincipal.add(new JLabel(" "));
        infoPrincipal.add(new JLabel("<html><b>Sobre " + pet.getNome() + ":</b><br>" + pet.getHistoria() + "</html>"));

        JPanel infoLateral = new JPanel(new GridLayout(0, 1, 8, 8));
        infoLateral.setBackground(Color.WHITE);
        infoLateral.setBorder(BorderFactory.createTitledBorder("Informações"));
        infoLateral.add(new JLabel("Tipo: " + pet.getEspecie()));
        infoLateral.add(new JLabel("Porte: " + pet.getPorte()));
        infoLateral.add(new JLabel("Idade: " + pet.getIdade() + " ano(s)"));
        infoLateral.add(new JLabel("Status: " + pet.getStatus().getDescricao()));
        infoLateral.add(new JLabel("Doador: " + pet.getDoador().getNome()));

        center.add(imagem);
        center.add(infoPrincipal);
        center.add(infoLateral);

        JButton adotarBtn = new JButton("Quero Adotar");
        adotarBtn.addActionListener(e -> {
            if (usuarioService.getUsuarioLogado() == null) {
                JOptionPane.showMessageDialog(this, "Faça login para solicitar adoção.");
                new LoginView();
            } else {
                new AdocaoView(pet);
                dispose();
            }
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(214, 226, 243));
        bottom.add(adotarBtn);

        root.add(top, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        setVisible(true);
    }

    private JLabel criarTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(new Color(35, 91, 174));
        return label;
    }
}