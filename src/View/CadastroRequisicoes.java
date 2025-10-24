package View;

import Controller.LivroController;
import Controller.RequisicoesController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CadastroRequisicoes extends JFrame{
    private JTextField inserirAno;
    private JTextField inserirAutor;
    private JTextField inserirTitulo;
    private JButton adicionarButton;
    private JPanel painelCL;
    private JLabel labelTitulo;
    private JLabel labelAno;
    private JLabel labelAutor;

    private JLabel label;
    private JButton voltar;

    public CadastroRequisicoes(){
        setContentPane(painelCL);
        setTitle("Sugerir Livros");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);

        Color botaoCor = new Color(39, 83, 129);
        Color branco = new Color(255, 255, 255);
        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 18);
        Font fonteLabel = new Font("Segoe UI Semibold", Font.PLAIN, 20);
        Font titulo = new Font("Segoe UI", Font.BOLD, 22);
        Font categorias = new Font("Segoe UI", Font.BOLD, 18);

        JLabel labelTexto = new JLabel("Digite um titulo que não existe no nosso acervo e voce gostária de ler.");
        JLabel labelTexto2 = new JLabel("Buscamos sempre aumentar nosso acervo com as suas sugestões");
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        labelTexto.setFont(fonteLabel);
        labelTexto2.setHorizontalAlignment(SwingConstants.CENTER);
        labelTexto2.setFont(fonteLabel);

        JPanel labels = new JPanel();
        EmptyBorder borda = new EmptyBorder(60,10,100,10);
        labels.add(labelTexto);
        labels.add(labelTexto2);
        labels.setBorder(borda);

        inserirAno.setFont(fontePadrao);
        inserirAutor.setFont(fontePadrao);
        inserirTitulo.setFont(fontePadrao);

        labelTitulo.setFont(categorias);
        labelAno.setFont(categorias);
        labelAutor.setFont(categorias);

        JPanel topo = new JPanel();
        topo.setLayout(new BorderLayout());
        topo.setBackground(new Color(45, 95, 150));
        topo.setPreferredSize(new Dimension(1000, 60));

        label = new JLabel("  Sugerir Livros");

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
        painelCL.add(labels, BorderLayout.CENTER);


        adicionarButton.addActionListener(this::botaoRequisicoesPerformed);
    }



    private void botaoRequisicoesPerformed(java.awt.event.ActionEvent evt){
        try {
            String titulo = inserirTitulo.getText();
            String autor = inserirAutor.getText();
            String ano = inserirAno.getText();

            if (titulo.isEmpty() || autor.isEmpty() || ano.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os campos.");
                return;
            }

            int anoInt = Integer.parseInt(ano);

            if (anoInt < 1000 || anoInt>2025) {
                JOptionPane.showMessageDialog(null, "O ano é invalido");
                return;
            }

            RequisicoesController controller = new RequisicoesController();
            controller.realizarRequisicao(titulo,autor,ano);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "PrincipalWiew" + e);
        }

    }
    private void voltarTelaPrincipal(java.awt.event.ActionEvent evt){
        TelaPrincipalUsuario tela = new TelaPrincipalUsuario();
        tela.setVisible(true);
        dispose();

    }
}
