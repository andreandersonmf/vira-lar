package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Adotante;
import model.Doador;
import model.Pet;
import model.Usuario;
import service.FavoritoService;
import service.PetService;
import service.UsuarioService;

public class DetalhePetView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();
    private final FavoritoService favoritoService = new FavoritoService();
    private final PetService petService = new PetService();

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

        JLabel imagem = criarImagemPet(pet, 260, 300);

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

        JButton favoritoBtn = new JButton("Favoritar");
        Usuario usuarioAtual = usuarioService.getUsuarioLogado();
        if (usuarioAtual instanceof Adotante adotante && favoritoService.ehFavorito(adotante, pet)) {
            favoritoBtn.setText("Remover favorito");
        }

        favoritoBtn.addActionListener(e -> {
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
                JOptionPane.showMessageDialog(this,
                        "Pet favoritado com sucesso! Acesse seu perfil para ver seus favoritos.");
            }

            new DetalhePetView(pet);
            dispose();
        });

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
        bottom.add(favoritoBtn);
        bottom.add(adotarBtn);

        if (usuarioAtual instanceof Doador doador && pet.getDoador().getId() == doador.getId()) {
            JButton editarBtn = new JButton("Editar pet");
            editarBtn.addActionListener(e -> {
                new DoacaoView(pet);
                dispose();
            });

            JButton excluirBtn = new JButton("Excluir pet");
            excluirBtn.addActionListener(e -> {
                int confirmacao = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja excluir o pet " + pet.getNome() + "?",
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    try {
                        petService.excluirPet(pet, doador);
                        JOptionPane.showMessageDialog(this, "Pet excluído com sucesso.");
                        new HomeView();
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    }
                }
            });

            bottom.add(editarBtn);
            bottom.add(excluirBtn);
        }

        root.add(top, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        setVisible(true);
    }

    private JLabel criarImagemPet(Pet pet, int largura, int altura) {
        JLabel label = new JLabel("Sem imagem", SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        String caminho = pet.getImagemPath();
        if (caminho == null || caminho.isBlank()) {
            return label;
        }

        ImageIcon icon = null;
        try {
            if (caminho.startsWith("/")) {
                URL url = getClass().getResource(caminho);
                if (url != null) {
                    icon = new ImageIcon(url);
                }
            } else {
                icon = new ImageIcon(caminho);
            }

            if (icon != null && icon.getIconWidth() > 0) {
                Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                label.setText("");
            }
        } catch (Exception ex) {
            label.setText("Sem imagem");
        }

        return label;
    }

    private JLabel criarTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(new Color(35, 91, 174));
        return label;
    }
}
