package View;

import Controller.LivroController;

import javax.swing.*;
import java.awt.*;

public class CadastroLivros extends JFrame{
    private JTextField inserirAno;
    private JTextField inserirAutor;
    private JTextField inserirCategoria;
    private JTextField inserirTitulo;
    private JButton adicionarButton;
    private JTextField inserirNExemplares;
    private JPanel painelCL;
    private JLabel labelTitulo;
    private JLabel labelAno;
    private JLabel labelCat;
    private JLabel labelAutor;
    private JLabel labelExemplares;
    private JLabel label;
    private JButton voltar;

    public CadastroLivros(){
        setContentPane(painelCL);
        setTitle("Cadastro Livros");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        Color botaoCor = new Color(39, 83, 129);

        Color branco = new Color(255, 255, 255);
        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 18);
        Font titulo = new Font("Segoe UI", Font.BOLD, 22);
        Font categorias = new Font("Segoe UI", Font.BOLD, 18);

        inserirAno.setFont(fontePadrao);
        inserirAutor.setFont(fontePadrao);
        inserirCategoria.setFont(fontePadrao);
        inserirTitulo.setFont(fontePadrao);
        inserirNExemplares.setFont(fontePadrao);

        labelTitulo.setFont(categorias);
        labelAno.setFont(categorias);
        labelCat.setFont(categorias);
        labelAutor.setFont(categorias);
        labelExemplares.setFont(categorias);

        JPanel topo = new JPanel();
        topo.setLayout(new BorderLayout());
        topo.setBackground(new Color(45, 95, 150));
        topo.setPreferredSize(new Dimension(1000, 60));

        label = new JLabel("  Cadastrar Livros");

        label.setFont(titulo);
        label.setForeground(branco);

        voltar = new JButton("Voltar");
        voltar.setFont(fontePadrao);
        voltar.addActionListener(evt -> voltarTelaPrincipal(evt));
        voltar.setBackground(botaoCor);
        voltar.setForeground(branco);

        adicionarButton.setForeground(branco);
        adicionarButton.setFont(fontePadrao);
        adicionarButton.setBackground(botaoCor);

        topo.add(label, BorderLayout.CENTER);
        topo.add(voltar, BorderLayout.WEST);
        painelCL.setLayout(new BorderLayout());
        painelCL.add(topo, BorderLayout.NORTH);
        adicionarButton.addActionListener(this::botaoCadastrarPerformed);
    }



    private void botaoCadastrarPerformed(java.awt.event.ActionEvent evt){
        try {
            String titulo = inserirTitulo.getText();
            String autor = inserirAutor.getText();
            String ano = inserirAno.getText();
            String categoria = inserirCategoria.getText();
            String nExemplares = inserirNExemplares.getText();

            if (titulo.isEmpty() || autor.isEmpty() || ano.isEmpty() || categoria.isEmpty() || nExemplares.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os campos.");
                return;
            }
            if (!ano.matches("[0-9]+")){
                JOptionPane.showMessageDialog(null,"Digite apenas numeros no ano.");
                return;
            }
            if (!nExemplares.matches("[0-9]+")){
                JOptionPane.showMessageDialog(null,"Digite apenas numeros no numero de exemplares.");
                return;
            }

            int anoInt = Integer.parseInt(ano);
            int nExemplaresInt = Integer.parseInt(nExemplares);

            if (anoInt < 1000 || anoInt>2025) {
                JOptionPane.showMessageDialog(null, "O ano é invalido");
                return;
            }

            if (nExemplaresInt <= 0){
                JOptionPane.showMessageDialog(null, "O numero de exemplares é invalido");
                return;
            }

            LivroController controller = new LivroController();
            controller.realizarEmprestimo(titulo,autor,ano,categoria,nExemplares);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "PrincipalWiew" + e);
        }
    }
    private void voltarTelaPrincipal(java.awt.event.ActionEvent evt){
        TelaPrincipalAdm tela = new TelaPrincipalAdm();
        tela.setVisible(true);
        dispose();

    }
}
