package View;

import Controller.LivroController;
import DAO.LivroDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaGerenciarLivros extends JFrame {

    private JTable tabelaLivros;
    private JTextField campoBusca;
    private JButton botaoBuscar;
    private JButton botaoAlterar;
    private JButton botaoApagar;
    private JButton botaoVoltar;
    private JLabel labelTitulo;

    public TelaGerenciarLivros() {

        setTitle("Gerenciar Livros");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        Color branco = new Color(255, 255, 255);
        Color azulEscuro = new Color(45, 95, 150);
        Color azulMedio = new Color(39, 84, 133);
        Color grade = new Color(180, 180, 180);

        Font fonteDefault = new Font("Segoe UI", Font.PLAIN, 16);
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fonteBotao = new Font("Segoe UI", Font.PLAIN, 16);
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 16);

        Dimension tamanhoTopo = new Dimension(1000, 60);
        Dimension tamanhoBotao = new Dimension(120, 40);

        EmptyBorder margemPainel = new EmptyBorder(20, 40, 10, 40);
        EmptyBorder bordaTabela = new EmptyBorder(20, 40, 40, 40);
        LineBorder bordaPreta = new LineBorder(Color.BLACK, 1);


        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(azulEscuro);
        topo.setPreferredSize(tamanhoTopo);

        labelTitulo = new JLabel("  Gerenciar Livros");
        labelTitulo.setFont(fonteTitulo);
        labelTitulo.setForeground(branco);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(fonteDefault);
        botaoVoltar.setBackground(azulMedio);
        botaoVoltar.setForeground(branco);
        botaoVoltar.setFocusPainted(false);

        topo.add(botaoVoltar, BorderLayout.WEST);
        topo.add(labelTitulo, BorderLayout.CENTER);
        add(topo, BorderLayout.NORTH);


        JPanel painelBusca = new JPanel(new BorderLayout(20, 10));
        painelBusca.setBackground(branco);
        painelBusca.setBorder(margemPainel);

        JLabel labelBusca = new JLabel("Digite o título do livro para buscar:");
        labelBusca.setFont(fonteLabel);

        campoBusca = new JTextField();
        campoBusca.setFont(fonteDefault);

        JPanel botoesBusca = new JPanel(new GridLayout(1, 3, 10, 0));
        botoesBusca.setBackground(branco);

        botaoBuscar = new JButton("Buscar");
        botaoBuscar.setFont(fonteBotao);
        botaoBuscar.setBackground(azulMedio);
        botaoBuscar.setForeground(branco);
        botaoBuscar.setPreferredSize(tamanhoBotao);
        botaoBuscar.setFocusPainted(false);

        botaoAlterar = new JButton("Alterar");
        botaoAlterar.setFont(fonteBotao);
        botaoAlterar.setBackground(azulMedio);
        botaoAlterar.setForeground(branco);
        botaoAlterar.setPreferredSize(tamanhoBotao);
        botaoAlterar.setFocusPainted(false);

        botaoApagar = new JButton("Apagar");
        botaoApagar.setFont(fonteBotao);
        botaoApagar.setBackground(azulMedio);
        botaoApagar.setForeground(branco);
        botaoApagar.setPreferredSize(tamanhoBotao);
        botaoApagar.setFocusPainted(false);

        botoesBusca.add(botaoBuscar);
        botoesBusca.add(botaoAlterar);
        botoesBusca.add(botaoApagar);

        painelBusca.add(labelBusca, BorderLayout.NORTH);
        painelBusca.add(campoBusca, BorderLayout.CENTER);
        painelBusca.add(botoesBusca, BorderLayout.EAST);


        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"Título", "Autor", "Categoria", "Ano", "Alugados", "Quantidade"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        tabelaLivros = new JTable(modeloTabela);
        tabelaLivros.setFont(fonteDefault);
        tabelaLivros.setRowHeight(28);
        tabelaLivros.getTableHeader().setFont(fonteDefault);
        tabelaLivros.setGridColor(grade);
        tabelaLivros.setFillsViewportHeight(true);

        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(280);
        tabelaLivros.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaLivros.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabelaLivros.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelaLivros.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaLivros.getColumnModel().getColumn(5).setPreferredWidth(100);
        montarTabela();

        JScrollPane scroll = new JScrollPane(tabelaLivros);
        scroll.setBorder(BorderFactory.createCompoundBorder(bordaTabela, bordaPreta));
        scroll.setBackground(branco);

        painelPrincipal.add(scroll, BorderLayout.CENTER);
        add(painelPrincipal, BorderLayout.CENTER);

        botaoVoltar.addActionListener(evt -> voltarTelaPrincipal(evt));
        botaoBuscar.addActionListener(evt -> botaoBuscarPerformed(evt));
        botaoApagar.addActionListener(evt -> botaoApagarPerformed(evt));
        botaoAlterar.addActionListener(evt -> botaoAlterarPerformed(evt));

        setVisible(true);
    }

    public void botaoAlterarPerformed(java.awt.event.ActionEvent evt) {
        try {

            int linha = tabelaLivros.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um livro para alterar.");
                return;
            }

            String titulo = tabelaLivros.getValueAt(linha, 0).toString();
            String autor = tabelaLivros.getValueAt(linha, 1).toString();
            String quantidade = tabelaLivros.getValueAt(linha, 5).toString();

            if (quantidade.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite uma quantidade válida.");
                return;
            }
            System.out.println(quantidade);
            int quantidadeInt = Integer.parseInt(quantidade);

            LivroDAO livroDAO = new LivroDAO();
            int linhasAfetadas = livroDAO.atualizarLivro(quantidadeInt, titulo, autor);

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");
                montarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado. Verifique os dados informados.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "A quantidade deve ser um número inteiro.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o livro: " + e.getMessage());
        }
    }

    public void botaoApagarPerformed(java.awt.event.ActionEvent evt) {
        try {
            int linha = tabelaLivros.getSelectedRow();
            String titulo = (String) tabelaLivros.getValueAt(linha, 0);
            String autor = (String) tabelaLivros.getValueAt(linha, 1);

            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione o título do livro.");
                return;
            }

            LivroController controller = new LivroController();
            controller.apagarLivro(titulo, autor);
            montarTabela();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Botao buscar na TelaGerenciarLivros: metodo botaoApagar - View. Erro: " + e);
        }

    }

    public void botaoBuscarPerformed(java.awt.event.ActionEvent evt) {
        try {
            String titulo = campoBusca.getText();

            if (titulo.equals("")) {
                montarTabela();
                JOptionPane.showMessageDialog(null, "Digite o titulo.");
                return;
            }
            montarTabela(titulo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Botao buscar na TelaGerenciarLivros: metodo botaoBuscar  - View. Erro: " + e);
        }

    }


    public void montarTabela() {
        try {
            LivroController controller = new LivroController();
            ResultSet acervo = controller.todosLivros();
            DefaultTableModel dados = (DefaultTableModel) tabelaLivros.getModel();
            dados.setRowCount(0);

            while (acervo.next()) {
                String titulo = acervo.getString("titulo");
                String autor = acervo.getString("autor");
                String categoria = acervo.getString("categoria");
                int ano = acervo.getInt("ano_publicacao");
                int total = acervo.getInt("numero_exemplares");
                int alugados = acervo.getInt("numero_emprestados");



                dados.addRow(new Object[]{titulo, autor, categoria, ano, alugados, total});
            }
        } catch (SQLException e) {
            throw new RuntimeException("erro no montartabela() - gerenciar livros)" + e.getMessage());
        }
    }

    public void montarTabela(String tituloBuscar) {
        try {
            LivroController controller = new LivroController();
            ResultSet livro = controller.buscarLivros(tituloBuscar);
            DefaultTableModel dados = (DefaultTableModel) tabelaLivros.getModel();

            dados.setRowCount(0);

            while (livro.next()) {
                String titulo = livro.getString("titulo");
                String autor = livro.getString("autor");
                String categoria = livro.getString("categoria");
                int ano = livro.getInt("ano_publicacao");
                int total = livro.getInt("numero_exemplares");
                int alugados = livro.getInt("numero_emprestados");

                dados.addRow(new Object[]{titulo, autor, categoria, ano, alugados, total});
            }

        } catch (SQLException e) {
            throw new RuntimeException("O erro foi no metododo montarTabela() - classe TelaGerenciarLivros. Erro: " + e.getMessage());
        }
    }


    private void voltarTelaPrincipal(java.awt.event.ActionEvent evt) {
        TelaPrincipalAdm tela = new TelaPrincipalAdm();
        tela.setVisible(true);
        dispose();

    }
}
