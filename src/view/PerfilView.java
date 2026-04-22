package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Adocao;
import model.Adotante;
import model.Doador;
import model.Pet;
import model.Usuario;
import service.AdocaoService;
import service.PetService;
import service.UsuarioService;

public class PerfilView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();
    private final PetService petService = new PetService();
    private final AdocaoService adocaoService = new AdocaoService();

    public PerfilView() {
        Usuario usuario = usuarioService.getUsuarioLogado();

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Faça login para acessar o perfil.");
            new LoginView();
            dispose();
            return;
        }

        setTitle("ViraLar - Meu Perfil");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(214, 226, 243));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(e -> {
            new HomeView();
            dispose();
        });

        JLabel titulo = new JLabel("Meu Perfil");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(35, 91, 174));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            usuarioService.logout();
            new HomeView();
            dispose();
        });

        top.add(voltarBtn, BorderLayout.WEST);
        top.add(titulo, BorderLayout.CENTER);
        top.add(logoutBtn, BorderLayout.EAST);

        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Dados do usuário"));
        infoPanel.add(new JLabel("Nome: " + usuario.getNome()));
        infoPanel.add(new JLabel("Tipo: " + usuario.getTipoUsuario()));
        infoPanel.add(new JLabel("E-mail: " + usuario.getEmail()));
        infoPanel.add(new JLabel("Telefone: " + usuario.getTelefone()));

        JPanel center = new JPanel(new GridLayout(1, 2, 20, 20));
        center.setBackground(new Color(214, 226, 243));
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        center.add(infoPanel);

        JPanel conteudoDinamico = new JPanel(new BorderLayout());
        conteudoDinamico.setBackground(Color.WHITE);

        if (usuario instanceof Doador doador) {
            JPanel petsPanel = new JPanel(new GridLayout(0, 1, 8, 8));
            petsPanel.setBackground(Color.WHITE);
            petsPanel.setBorder(BorderFactory.createTitledBorder("Pets cadastrados"));

            List<Pet> pets = petService.petsDoDoador(doador);
            if (pets.isEmpty()) {
                petsPanel.add(new JLabel("Nenhum pet cadastrado."));
            } else {
                for (Pet pet : pets) {
                    JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    item.setBackground(Color.WHITE);
                    item.add(new JLabel(pet.getNome() + " - " + pet.getEspecie() + " - " + pet.getStatus().getDescricao()));
                    JButton detalhesBtn = new JButton("Detalhes");
                    detalhesBtn.addActionListener(e -> {
                        new DetalhePetView(pet);
                        dispose();
                    });
                    item.add(detalhesBtn);
                    petsPanel.add(item);
                }
            }

            conteudoDinamico.add(petsPanel, BorderLayout.CENTER);
        } else if (usuario instanceof Adotante adotante) {
            JPanel adocoesPanel = new JPanel(new GridLayout(0, 1, 8, 8));
            adocoesPanel.setBackground(Color.WHITE);
            adocoesPanel.setBorder(BorderFactory.createTitledBorder("Minhas solicitações"));

            List<Adocao> adocoes = adocaoService.listarDoAdotante(adotante);
            if (adocoes.isEmpty()) {
                adocoesPanel.add(new JLabel("Nenhuma adoção solicitada."));
            } else {
                for (Adocao adocao : adocoes) {
                    JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    item.setBackground(Color.WHITE);
                    item.add(new JLabel(adocao.getPet().getNome() + " - " + adocao.getStatus().getDescricao() + " - " + adocao.getDataSolicitacao()));
                    adocoesPanel.add(item);
                }
            }

            conteudoDinamico.add(adocoesPanel, BorderLayout.CENTER);
        }

        center.add(conteudoDinamico);

        root.add(top, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }
}