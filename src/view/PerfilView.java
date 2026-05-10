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
import javax.swing.JScrollPane;

import model.Adocao;
import model.Adotante;
import model.Doador;
import model.Pet;
import model.StatusAdocao;
import model.Usuario;
import service.AdocaoService;
import service.FavoritoService;
import service.PetService;
import service.UsuarioService;

public class PerfilView extends JFrame {
    private final UsuarioService usuarioService = new UsuarioService();
    private final PetService petService = new PetService();
    private final AdocaoService adocaoService = new AdocaoService();
    private final FavoritoService favoritoService = new FavoritoService();

    public PerfilView() {
        Usuario usuario = usuarioService.getUsuarioLogado();

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Faça login para acessar o perfil.");
            new LoginView();
            dispose();
            return;
        }

        setTitle("ViraLar - Meu Perfil");
        setSize(1100, 720);
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

        JPanel conteudoDinamico = new JPanel(new GridLayout(2, 1, 12, 12));
        conteudoDinamico.setBackground(new Color(214, 226, 243));

        if (usuario instanceof Doador doador) {
            conteudoDinamico.add(new JScrollPane(montarPetsDoDoador(doador)));
            conteudoDinamico.add(new JScrollPane(montarSolicitacoesRecebidas(doador)));
        } else if (usuario instanceof Adotante adotante) {
            conteudoDinamico.add(new JScrollPane(montarMinhasSolicitacoes(adotante)));
            conteudoDinamico.add(new JScrollPane(montarMeusFavoritos(adotante)));
        }

        center.add(conteudoDinamico);

        root.add(top, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }

    private JPanel montarPetsDoDoador(Doador doador) {
        JPanel petsPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        petsPanel.setBackground(Color.WHITE);
        petsPanel.setBorder(BorderFactory.createTitledBorder("Pets cadastrados"));

        List<Pet> pets = petService.petsDoDoador(doador);
        if (pets.isEmpty()) {
            petsPanel.add(new JLabel("Nenhum pet cadastrado."));
            return petsPanel;
        }

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

            JButton editarBtn = new JButton("Editar");
            editarBtn.addActionListener(e -> {
                new DoacaoView(pet);
                dispose();
            });

            JButton excluirBtn = new JButton("Excluir");
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

            item.add(editarBtn);
            item.add(excluirBtn);
            petsPanel.add(item);
        }

        return petsPanel;
    }

    private JPanel montarSolicitacoesRecebidas(Doador doador) {
        JPanel solicitacoesPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        solicitacoesPanel.setBackground(Color.WHITE);
        solicitacoesPanel.setBorder(BorderFactory.createTitledBorder("Solicitações recebidas"));

        List<Adocao> adocoes = adocaoService.listarDoDoador(doador);
        if (adocoes.isEmpty()) {
            solicitacoesPanel.add(new JLabel("Nenhuma solicitação recebida."));
            return solicitacoesPanel;
        }

        for (Adocao adocao : adocoes) {
            JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
            item.setBackground(Color.WHITE);
            item.add(new JLabel(adocao.getPet().getNome()
                    + " - Adotante: " + adocao.getAdotante().getNome()
                    + " - " + adocao.getStatus().getDescricao()
                    + " - " + adocao.getDataSolicitacao()));

            if (adocao.getStatus() == StatusAdocao.EM_ANALISE) {
                JButton aprovarBtn = new JButton("Aprovar");
                aprovarBtn.addActionListener(e -> {
                    adocaoService.aprovarAdocao(adocao);
                    JOptionPane.showMessageDialog(this, "Solicitação aprovada.");
                    new PerfilView();
                    dispose();
                });

                JButton recusarBtn = new JButton("Recusar");
                recusarBtn.addActionListener(e -> {
                    adocaoService.recusarAdocao(adocao);
                    JOptionPane.showMessageDialog(this, "Solicitação recusada.");
                    new PerfilView();
                    dispose();
                });

                item.add(aprovarBtn);
                item.add(recusarBtn);
            }

            solicitacoesPanel.add(item);
        }

        return solicitacoesPanel;
    }

    private JPanel montarMinhasSolicitacoes(Adotante adotante) {
        JPanel adocoesPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        adocoesPanel.setBackground(Color.WHITE);
        adocoesPanel.setBorder(BorderFactory.createTitledBorder("Minhas solicitações"));

        List<Adocao> adocoes = adocaoService.listarDoAdotante(adotante);
        if (adocoes.isEmpty()) {
            adocoesPanel.add(new JLabel("Nenhuma adoção solicitada."));
            return adocoesPanel;
        }

        for (Adocao adocao : adocoes) {
            JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
            item.setBackground(Color.WHITE);
            item.add(new JLabel(adocao.getPet().getNome()
                    + " - " + adocao.getStatus().getDescricao()
                    + " - " + adocao.getDataSolicitacao()));
            adocoesPanel.add(item);
        }

        return adocoesPanel;
    }

    private JPanel montarMeusFavoritos(Adotante adotante) {
        JPanel favoritosPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        favoritosPanel.setBackground(Color.WHITE);
        favoritosPanel.setBorder(BorderFactory.createTitledBorder("Meus favoritos"));

        List<Pet> favoritos = favoritoService.listarPetsFavoritos(adotante);
        if (favoritos.isEmpty()) {
            favoritosPanel.add(new JLabel("Nenhum pet favoritado."));
            return favoritosPanel;
        }

        for (Pet pet : favoritos) {
            JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
            item.setBackground(Color.WHITE);
            item.add(new JLabel(pet.getNome() + " - " + pet.getEspecie() + " - " + pet.getStatus().getDescricao()));

            JButton detalhesBtn = new JButton("Detalhes");
            detalhesBtn.addActionListener(e -> {
                new DetalhePetView(pet);
                dispose();
            });

            JButton removerBtn = new JButton("Remover favorito");
            removerBtn.addActionListener(e -> {
                favoritoService.removerFavorito(adotante, pet);
                new PerfilView();
                dispose();
            });

            item.add(detalhesBtn);
            item.add(removerBtn);
            favoritosPanel.add(item);
        }

        return favoritosPanel;
    }
}
