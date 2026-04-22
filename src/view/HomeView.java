package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import model.Pet;
import model.Usuario;
import service.PetService;
import service.UsuarioService;

public class HomeView extends JFrame {
    private final PetService petService = new PetService();
    private final UsuarioService usuarioService = new UsuarioService();

    private final JPanel petsPanel = new JPanel();
    private final JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Todos", "Cachorro", "Gato"});
    private final JComboBox<String> idadeCombo = new JComboBox<>(new String[]{"Todas", "Até 1 ano", "2 a 4 anos", "5+ anos"});
    private final JComboBox<String> localCombo = new JComboBox<>(new String[]{"Todas", "São Paulo", "Guarulhos", "Osasco", "Santo André"});
    private final JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    public HomeView() {
        setTitle("ViraLar - Home");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(214, 226, 243));

        JPanel header = montarHeader();
        JPanel hero = montarHero();
        JPanel busca = montarBusca();

        petsPanel.setLayout(new GridLayout(0, 4, 15, 15));
        petsPanel.setBackground(Color.WHITE);
        petsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(petsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Animais disponíveis para adoção"));

        JPanel center = new JPanel(new BorderLayout());
        center.add(hero, BorderLayout.NORTH);
        center.add(busca, BorderLayout.CENTER);
        center.add(scrollPane, BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
        atualizarTopoDireito();
        atualizarLista(petService.listarTodos());

        setVisible(true);
    }

    private JPanel montarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel logo = new JLabel("<html><span style='color:#245BB2;font-size:24px;'><b>VIRALAR</b></span><br><span style='color:#F48C06;'>toda patinha merece um lar!</span></html>");

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        menu.setBackground(Color.WHITE);

        JButton inicioBtn = new JButton("Início");
        JButton adotarBtn = new JButton("Adotar um Pet");
        JButton doarBtn = new JButton("Doar um Pet");
        JButton perfilBtn = new JButton("Meu Perfil");

        inicioBtn.addActionListener(e -> {
            new HomeView();
            dispose();
        });

        adotarBtn.addActionListener(e -> {
            Usuario usuario = usuarioService.getUsuarioLogado();
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Faça login para continuar.");
                new LoginView();
            } else {
                new AdocaoView(null);
                dispose();
            }
        });

        doarBtn.addActionListener(e -> {
            Usuario usuario = usuarioService.getUsuarioLogado();
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Faça login para continuar.");
                new LoginView();
            } else {
                new DoacaoView();
                dispose();
            }
        });

        perfilBtn.addActionListener(e -> {
            Usuario usuario = usuarioService.getUsuarioLogado();
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Você precisa estar logado.");
                new LoginView();
            } else {
                new PerfilView();
                dispose();
            }
        });

        menu.add(inicioBtn);
        menu.add(adotarBtn);
        menu.add(doarBtn);
        menu.add(perfilBtn);

        topRightPanel.setBackground(Color.WHITE);

        header.add(logo, BorderLayout.WEST);
        header.add(menu, BorderLayout.CENTER);
        header.add(topRightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel montarHero() {
        JPanel hero = new JPanel();
        hero.setBackground(new Color(214, 226, 243));
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Encontre seu novo amigo!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(35, 91, 174));
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        botoes.setBackground(new Color(214, 226, 243));

        JButton queroAdotar = new JButton("Quero Adotar");
        JButton queroDoar = new JButton("Quero Doar");
        queroAdotar.setPreferredSize(new Dimension(180, 45));
        queroDoar.setPreferredSize(new Dimension(180, 45));

        queroAdotar.addActionListener(e -> {
            if (usuarioService.getUsuarioLogado() == null) {
                JOptionPane.showMessageDialog(this, "Faça login para solicitar adoção.");
                new LoginView();
            } else {
                new AdocaoView(null);
                dispose();
            }
        });

        queroDoar.addActionListener(e -> {
            if (usuarioService.getUsuarioLogado() == null) {
                JOptionPane.showMessageDialog(this, "Faça login para doar um pet.");
                new LoginView();
            } else {
                new DoacaoView();
                dispose();
            }
        });

        botoes.add(queroAdotar);
        botoes.add(queroDoar);

        hero.add(titulo);
        hero.add(botoes);
        return hero;
    }

    private JPanel montarBusca() {
        JPanel busca = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        busca.setBackground(new Color(214, 226, 243));

        JButton buscarBtn = new JButton("Buscar");

        buscarBtn.addActionListener(e -> {
            String tipo = (String) tipoCombo.getSelectedItem();
            String idade = (String) idadeCombo.getSelectedItem();
            String local = (String) localCombo.getSelectedItem();

            if ("Todas".equals(local)) {
                local = "Todas";
            }

            atualizarLista(petService.buscar(tipo, idade, local));
        });

        busca.add(tipoCombo);
        busca.add(idadeCombo);
        busca.add(localCombo);
        busca.add(buscarBtn);

        return busca;
    }

    private void atualizarLista(List<Pet> pets) {
        petsPanel.removeAll();

        for (Pet pet : pets) {
            petsPanel.add(criarCardPet(pet));
        }

        petsPanel.revalidate();
        petsPanel.repaint();
    }

    private JPanel criarCardPet(Pet pet) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(220, 220));
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel imagem = new JLabel(pet.getEspecie().equalsIgnoreCase("Gato") ? "🐱" : "🐶", SwingConstants.CENTER);
        imagem.setFont(new Font("Arial", Font.PLAIN, 48));

        JLabel nome = new JLabel(pet.getNome());
        nome.setFont(new Font("Arial", Font.BOLD, 18));
        nome.setForeground(new Color(35, 91, 174));

        JLabel info = new JLabel(pet.getIdade() + " ano(s) | " + pet.getPorte() + " | " + pet.getEspecie());
        JLabel status = new JLabel("Status: " + pet.getStatus().getDescricao());

        JPanel texto = new JPanel(new GridLayout(0, 1));
        texto.setBackground(new Color(245, 245, 245));
        texto.add(nome);
        texto.add(info);
        texto.add(status);

        JButton detalhes = new JButton("Detalhes");
        JButton adotar = new JButton("Adotar");

        detalhes.addActionListener(e -> {
            new DetalhePetView(pet);
            dispose();
        });

        adotar.addActionListener(e -> {
            if (usuarioService.getUsuarioLogado() == null) {
                JOptionPane.showMessageDialog(this, "Faça login para continuar.");
                new LoginView();
            } else {
                new AdocaoView(pet);
                dispose();
            }
        });

        JPanel botoes = new JPanel(new GridLayout(1, 2, 8, 0));
        botoes.setBackground(new Color(245, 245, 245));
        botoes.add(detalhes);
        botoes.add(adotar);

        card.add(imagem, BorderLayout.NORTH);
        card.add(texto, BorderLayout.CENTER);
        card.add(botoes, BorderLayout.SOUTH);

        return card;
    }

    private void atualizarTopoDireito() {
        topRightPanel.removeAll();

        Usuario usuario = usuarioService.getUsuarioLogado();
        if (usuario == null) {
            JButton loginBtn = new JButton("Login");
            loginBtn.addActionListener(e -> new LoginView());
            topRightPanel.add(loginBtn);
        } else {
            JLabel nome = new JLabel("Olá, " + usuario.getNome());
            JButton perfilBtn = new JButton("Perfil");
            JButton logoutBtn = new JButton("Logout");

            perfilBtn.addActionListener(e -> {
                new PerfilView();
                dispose();
            });

            logoutBtn.addActionListener(e -> {
                usuarioService.logout();
                new HomeView();
                dispose();
            });

            topRightPanel.add(nome);
            topRightPanel.add(perfilBtn);
            topRightPanel.add(logoutBtn);
        }

        topRightPanel.revalidate();
        topRightPanel.repaint();
    }
}