package View;

import Controller.EmprestimoController;
import Controller.LivroController;
import DAO.LivroDAO;
import Globals.UsuarioAtivo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaPrincipalUsuario extends JFrame{
    private JTable tabelaLivros;
    private JTextField barraBuscar;
    private JButton botaoEmprestar;
    private JButton botaoBuscar;
    private JButton botaoRequisicao;
    private JButton botaoFiltros;
    private JButton botaoHistoricoEmprestimos;
    private JLabel label;
    private JLabel labelAlugar;
    private JPanel panel1;

    public TelaPrincipalUsuario() {
        setTitle("Biblioteca Universitária Alunos");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color branco = new Color(255, 255, 255);
        Color corBotao = new Color(39, 84, 133);
        Color topoTabela = new Color(75, 130, 180, 255);
        Color cinzaClaro = new Color(235, 235, 235);
        Color azulTopo = new Color(45, 95, 150);
        Color grade = new Color(180, 180, 180);

        Font fonteDefault = new Font("Segoe UI", Font.PLAIN, 16);
        Font fonteBotao = new Font("Segoe UI", Font.PLAIN, 16);
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 16);
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);

        Dimension tamanhoTopo = new Dimension(1000, 60);
        Dimension tamanhoBotao = new Dimension(120, 40);

        EmptyBorder bordaLabel = new EmptyBorder(0, 10, 0, 200);
        EmptyBorder bordaBuscar = new EmptyBorder(20, 40, 10, 40);
        EmptyBorder bordaTabela = new EmptyBorder(20, 40, 40, 40);
        LineBorder bordaCor = new LineBorder(Color.BLACK, 1);

        JPanel lateral = new JPanel(new GridLayout(1, 3, 0, 0));
        lateral.setBackground(cinzaClaro);

        botaoRequisicao = new JButton("Solicitar");
        botaoFiltros = new JButton("Descobrir");
        botaoHistoricoEmprestimos = new JButton("Histórico");

        JButton[] botoesTopo = {botaoRequisicao, botaoFiltros, botaoHistoricoEmprestimos};
        for (JButton b : botoesTopo) {
            b.setFont(fonteBotao);
            b.setForeground(branco);
            b.setBackground(corBotao);
        }

        lateral.add(botaoRequisicao);
        lateral.add(botaoFiltros);
        lateral.add(botaoHistoricoEmprestimos);

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(azulTopo);
        topo.setPreferredSize(tamanhoTopo);

        label = new JLabel("   Biblioteca Universitária    ");
        label.setFont(fonteTitulo);
        label.setForeground(branco);
        label.setBorder(bordaLabel);

        topo.add(label, BorderLayout.WEST);
        topo.add(lateral, BorderLayout.CENTER);
        add(topo, BorderLayout.NORTH);

        JPanel buscar = new JPanel(new BorderLayout(20, 10));
        buscar.setBackground(branco);
        buscar.setBorder(bordaBuscar);

        labelAlugar = new JLabel("Digite o livro que quer alugar:");
        labelAlugar.setFont(fonteLabel);

        barraBuscar = new JTextField();
        barraBuscar.setFont(fonteDefault);

        JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 0));
        botoes.setBackground(branco);

        botaoEmprestar = new JButton("Alugar");
        botaoEmprestar.setFont(fonteBotao);
        botaoEmprestar.setBackground(topoTabela);
        botaoEmprestar.setForeground(branco);
        botaoEmprestar.setPreferredSize(tamanhoBotao);

        botaoBuscar = new JButton("Buscar");
        botaoBuscar.setFont(fonteBotao);
        botaoBuscar.setBackground(topoTabela);
        botaoBuscar.setForeground(branco);
        botaoBuscar.setPreferredSize(tamanhoBotao);

        botoes.add(botaoBuscar);
        botoes.add(botaoEmprestar);

        buscar.add(labelAlugar, BorderLayout.NORTH);
        buscar.add(barraBuscar, BorderLayout.CENTER);
        buscar.add(botoes, BorderLayout.EAST);

        panel1 = new JPanel(new BorderLayout());
        panel1.add(buscar, BorderLayout.NORTH);

        String[] colunas = {"Titulo", "Autor", "Categoria", "Ano", "Quant."};
        DefaultTableModel tabelaDados = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaLivros = new JTable(tabelaDados);
        tabelaLivros.setFont(fonteDefault);
        tabelaLivros.setRowHeight(28);
        tabelaLivros.getTableHeader().setFont(fonteDefault);
        tabelaLivros.setGridColor(grade);
        tabelaLivros.setFillsViewportHeight(true);
        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(330);
        tabelaLivros.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaLivros.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabelaLivros.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelaLivros.getColumnModel().getColumn(4).setPreferredWidth(70);

        JScrollPane scroll = new JScrollPane(tabelaLivros);
        scroll.setBorder(BorderFactory.createCompoundBorder(bordaTabela, bordaCor));
        scroll.setBackground(branco);

        panel1.add(scroll, BorderLayout.CENTER);
        add(panel1, BorderLayout.CENTER);

        montarTabela();
        botaoEmprestar.addActionListener(evt -> botaoEmprestarPerformed(evt));
        botaoRequisicao.addActionListener(evt -> requisicaoPerformed(evt));
        botaoBuscar.addActionListener(evt -> botaoBuscarPerformed(evt));
        botaoHistoricoEmprestimos.addActionListener(evt -> botaoHistoricoEmprestimosPerformed(evt));
        botaoFiltros.addActionListener(evt -> botaoFiltrosPerformed(evt));
    }

    public void botaoHistoricoEmprestimosPerformed(java.awt.event.ActionEvent evt) {
        TelaHistoricoUsuario historicoUsuario = new TelaHistoricoUsuario();
        historicoUsuario.setVisible(true);
        dispose();
    }
    public void botaoFiltrosPerformed(java.awt.event.ActionEvent evt) {
        TelaDescobrirLivros descobrirLivros = new TelaDescobrirLivros();
        descobrirLivros.setVisible(true);
        dispose();
    }

    private void requisicaoPerformed(java.awt.event.ActionEvent evt){
        CadastroRequisicoes solicitarTitulos = new CadastroRequisicoes();
        solicitarTitulos.setVisible(true);
        dispose();
    }
    public void botaoBuscarPerformed(java.awt.event.ActionEvent evt) {
        try {
            String titulo = barraBuscar.getText();

            if (titulo.equals("")) {
                montarTabela();
                JOptionPane.showMessageDialog(null, "Digite o titulo.");
                return;
            }
            montarTabela(titulo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Botao buscar na TelaPrincipalUsuario: metodo botaoBuscar  - View. Erro: " + e);
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
                int disponiveis = total-alugados;

                dados.addRow(new Object[]{titulo, autor, categoria, ano,disponiveis});
            }
        } catch (SQLException e) {
            throw new RuntimeException("erro no montartabela() - Principal Usuario" + e.getMessage());
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
                int disponiveis = total-alugados;

                dados.addRow(new Object[]{titulo, autor, categoria, ano,disponiveis});
            }

        } catch (SQLException e) {
            throw new RuntimeException("O erro foi no metododo montarTabela() - classe TelaPrincipalUsuario. Erro: " + e.getMessage());
        }
    }

    private void botaoEmprestarPerformed(java.awt.event.ActionEvent evt){
        try {
            int linha = tabelaLivros.getSelectedRow();
            String titulo = tabelaLivros.getValueAt(linha,1).toString();

            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione o título do livro.");
                return;
            }

            LivroController livroController = new LivroController();

            EmprestimoController controller = new EmprestimoController();
            controller.realizarEmprestimo(UsuarioAtivo.getUsuarioAtivo(), livroController.encontrarLivro(titulo));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Principal Usuario View deu erro : " + e);
        }
    }

}
