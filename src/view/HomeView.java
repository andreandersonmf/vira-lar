package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import java.awt.Image;
import java.awt.Cursor;


import model.Adotante;
import model.Pet;
import model.Usuario;
import service.FavoritoService;
import service.PetService;
import service.UsuarioService;

public class HomeView extends JFrame {
    private final PetService petService = new PetService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final FavoritoService favoritoService = new FavoritoService();

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
        root.setBackground(Theme.BACKGROUND);

        JPanel header = montarHeader();
        JPanel hero = montarHero();
        JPanel busca = montarBusca();

        petsPanel.setLayout(new GridLayout(0, 4, 18, 18));
        petsPanel.setBackground(Theme.BACKGROUND);
        petsPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JScrollPane scrollPane = new JScrollPane(petsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Theme.BACKGROUND);
        scrollPane.setBackground(Theme.BACKGROUND);

        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setUnitIncrement(16);

        JPanel listWrapper = new JPanel(new BorderLayout());
        listWrapper.setBackground(Theme.BACKGROUND);
        listWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel listTitle = new JLabel("Animais disponíveis para adoção");
        listTitle.setFont(Theme.SUBTITLE);
        listTitle.setForeground(Theme.TEXT);
        listTitle.setBorder(BorderFactory.createEmptyBorder(0, 4, 12, 0));

        listWrapper.add(listTitle, BorderLayout.NORTH);
        listWrapper.add(scrollPane, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(Theme.BACKGROUND);
        center.add(hero, BorderLayout.NORTH);
        center.add(busca, BorderLayout.CENTER);
        center.add(listWrapper, BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
        atualizarTopoDireito();
        atualizarLista(petService.listarDisponiveis());

        setVisible(true);
    }

    private JPanel montarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.CARD);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel logo = new JLabel("<html><span style='color:#245BB2;font-size:24px;'><b>VIRALAR</b></span><br><span style='color:#F48C06;'>toda patinha merece um lar!</span></html>");

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        menu.setBackground(Theme.CARD);

        JButton inicioBtn = new JButton("Início");
        JButton adotarBtn = new JButton("Adotar um Pet");
        JButton doarBtn = new JButton("Doar um Pet");
        JButton perfilBtn = new JButton("Meu Perfil");
        Theme.secondaryButton(inicioBtn);
        Theme.secondaryButton(adotarBtn);
        Theme.secondaryButton(doarBtn);
        Theme.secondaryButton(perfilBtn);

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

        topRightPanel.setBackground(Theme.CARD);

        header.add(logo, BorderLayout.WEST);
        header.add(menu, BorderLayout.CENTER);
        header.add(topRightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel montarHero() {
        JPanel hero = new JPanel();
        hero.setBackground(Theme.BACKGROUND);
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Encontre seu novo amigo!", SwingConstants.CENTER);
        titulo.setFont(Theme.TITLE);
        titulo.setForeground(Theme.PRIMARY);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        botoes.setBackground(Theme.BACKGROUND);

        JButton queroAdotar = new JButton("Quero Adotar");
        JButton queroDoar = new JButton("Quero Doar");
        queroAdotar.setPreferredSize(new Dimension(180, 45));
        queroDoar.setPreferredSize(new Dimension(180, 45));
        Theme.primaryButton(queroAdotar);
        Theme.secondaryButton(queroDoar);

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
        busca.setBackground(Theme.BACKGROUND);
        busca.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        Theme.styleCombo(tipoCombo);
        Theme.styleCombo(idadeCombo);
        Theme.styleCombo(localCombo);

        JButton buscarBtn = new JButton("Buscar");
        Theme.primaryButton(buscarBtn);

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
        card.setPreferredSize(new Dimension(240, 240));
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel imagem = new JLabel("", SwingConstants.CENTER);

        if (pet.getImagemPath() != null && !pet.getImagemPath().isBlank()) {
            try {
                ImageIcon icon;

                if (pet.getImagemPath().startsWith("/")) {
                    icon = new ImageIcon(getClass().getResource(pet.getImagemPath()));
                } else {
                    icon = new ImageIcon(pet.getImagemPath());
                }

                Image img = icon.getImage().getScaledInstance(160, 100, Image.SCALE_SMOOTH);
                imagem.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                imagem.setText("Sem imagem");
            }
        } else {
            imagem.setText("Sem imagem");
        }

        imagem.setForeground(Theme.TEXT);

        JLabel nome = new JLabel(pet.getNome());
        nome.setFont(Theme.SUBTITLE);
        nome.setForeground(Theme.PRIMARY);

        JLabel info = new JLabel(pet.getIdade() + " ano(s) | " + pet.getPorte() + " | " + pet.getEspecie());
        JLabel status = new JLabel("Status: " + pet.getStatus().getDescricao());
        info.setFont(Theme.NORMAL);
        status.setFont(Theme.NORMAL);

        info.setForeground(Theme.MUTED);
        status.setForeground(Theme.TEXT);

        JPanel texto = new JPanel(new GridLayout(0, 1));
        texto.setBackground(Theme.CARD);
        texto.setBorder(BorderFactory.createEmptyBorder(8, 0, 12, 0));
        texto.add(nome);
        texto.add(info);
        texto.add(status);

        JButton detalhes = new JButton("Detalhes");
        JButton favorito = new JButton("Favoritar");
        JButton adotar = new JButton("Adotar");
        Theme.secondaryButton(detalhes);
        Theme.secondaryButton(favorito);
        Theme.primaryButton(adotar);

        Usuario usuarioAtual = usuarioService.getUsuarioLogado();
        if (usuarioAtual instanceof Adotante adotante && favoritoService.ehFavorito(adotante, pet)) {
            favorito.setText("Favorito");
        }

        detalhes.addActionListener(e -> {
            new DetalhePetView(pet);
            dispose();
        });

        favorito.addActionListener(e -> {
            Usuario usuario = usuarioService.getUsuarioLogado();
            if (!(usuario instanceof Adotante adotante)) {
                JOptionPane.showMessageDialog(this, "Faça login como adotante para favoritar pets.");
                new LoginView();
                return;
            }

            boolean jaEraFavorito = favoritoService.ehFavorito(adotante, pet);

            favoritoService.alternarFavorito(adotante, pet);

            if (jaEraFavorito) {
                JOptionPane.showMessageDialog(this, "Pet removido dos favoritos.");
            } else {
                JOptionPane.showMessageDialog(this, "Pet favoritado com sucesso! Acesse seu perfil para ver seus favoritos.");
            }

            atualizarLista(petService.listarDisponiveis());
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

        JPanel botoes = new JPanel(new GridLayout(1, 3, 8, 0));
        botoes.setBackground(Theme.CARD);
        botoes.add(detalhes);
        botoes.add(favorito);
        botoes.add(adotar);

        card.add(imagem, BorderLayout.NORTH);
        card.add(texto, BorderLayout.CENTER);
        card.add(botoes, BorderLayout.SOUTH);

        return card;
    }

    private ImageIcon carregarIconeTema() {
        String caminho = Theme.darkMode ? "/resources/light.png" : "/resources/dark.png";

        ImageIcon icon = new ImageIcon(getClass().getResource(caminho));
        Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

        return new ImageIcon(img);
    }

    private void atualizarTopoDireito() {
        topRightPanel.removeAll();
        topRightPanel.setBackground(Theme.CARD);

        Usuario usuario = usuarioService.getUsuarioLogado();
        if (usuario == null) {
            JButton temaBtn = new JButton(carregarIconeTema());
            temaBtn.setPreferredSize(new Dimension(42, 42));
            temaBtn.setFocusPainted(false);
            temaBtn.setBorderPainted(false);
            temaBtn.setContentAreaFilled(false);
            temaBtn.setOpaque(false);
            temaBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));;

            temaBtn.addActionListener(e -> {
                Theme.darkMode = !Theme.darkMode;

                if (Theme.darkMode) {
                    Theme.applyDarkTheme();
                } else {
                    Theme.applyLightTheme();
                }

                new HomeView();
                dispose();
            });

            JButton loginBtn = new JButton("Login");
            Theme.primaryButton(loginBtn);

            loginBtn.addActionListener(e -> new LoginView());

            topRightPanel.add(temaBtn);
            topRightPanel.add(loginBtn);
        }
        else {
            JButton temaBtn = new JButton(carregarIconeTema());
            temaBtn.setPreferredSize(new Dimension(42, 42));
            temaBtn.setFocusPainted(false);
            temaBtn.setBorderPainted(false);
            temaBtn.setContentAreaFilled(false);
            temaBtn.setOpaque(false);
            temaBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            temaBtn.addActionListener(e -> {
                Theme.darkMode = !Theme.darkMode;

                if (Theme.darkMode) {
                    Theme.applyDarkTheme();
                } else {
                    Theme.applyLightTheme();
                }

                new HomeView();
                dispose();
            });

            JLabel nome = new JLabel("Olá, " + usuario.getNome());
            nome.setForeground(Theme.TEXT);

            JButton perfilBtn = new JButton("Perfil");
            JButton logoutBtn = new JButton("Logout");

            Theme.secondaryButton(perfilBtn);
            Theme.primaryButton(logoutBtn);

            perfilBtn.addActionListener(e -> {
                new PerfilView();
                dispose();
            });

            logoutBtn.addActionListener(e -> {
                usuarioService.logout();
                new HomeView();
                dispose();
            });

        topRightPanel.add(temaBtn);
        topRightPanel.add(nome);
        topRightPanel.add(perfilBtn);
        topRightPanel.add(logoutBtn);
        }

        topRightPanel.revalidate();
        topRightPanel.repaint();
    }
}