package View;

import Controller.EmprestimoController;
import DAO.LivroDAO;
import Globals.UsuarioAtivo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Principal extends JFrame {
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JButton botaoEmprestar;
    private JButton botaoDevolucao;
    private JLabel labelAlugar;
    private JTable tabelaLivros;
    private JScrollPane scroll;
    private JButton cadastrarLivros;
    private JButton button2;
    private JButton button3;
    private JTextField textArea1;

    public Principal() {
        setContentPane(panelPrincipal);
        setTitle("Biblioteca Universitária");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Font fonteDefault = new Font("Segoe UI", Font.PLAIN, 16);
        Dimension dimensionDefault = new Dimension(0,40);
        Dimension tamanhoBotao = new Dimension(40, 40);
        Color cor = new Color(53,72,94);

        labelAlugar.setFont(fonteDefault);
        botaoDevolucao.setMaximumSize(tamanhoBotao);
        botaoDevolucao.setFont(fonteDefault);
        botaoEmprestar.setMaximumSize(tamanhoBotao);
        botaoEmprestar.setFont(fonteDefault);
        textField1.setPreferredSize(dimensionDefault);
        textField1.setFont(fonteDefault);

        String[] colunas = { "Titulo", "Autor", "Categoria", "Ano", "Quant."};
        DefaultTableModel tabelaDados = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede editar
            }
        };
        tabelaLivros.setModel(tabelaDados);
        tabelaLivros.setFont(fonteDefault);
        tabelaLivros.setRowHeight(28);
        tabelaLivros.getTableHeader().setFont(fonteDefault);
        tabelaLivros.setGridColor(new Color(180,180,180));
        tabelaLivros.setFillsViewportHeight(true);

        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(330);
        tabelaLivros.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaLivros.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabelaLivros.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelaLivros.getColumnModel().getColumn(4).setPreferredWidth(70);

        montarTabela();

        botaoEmprestar.addActionListener(evt -> botaoEmprestarPerformed(evt));
        botaoDevolucao.addActionListener(evt -> botaoDevolucaoPerformed(evt));
        cadastrarLivros.addActionListener(evt -> cadastrarLivrosPerformed(evt));

    }
    private void cadastrarLivrosPerformed(java.awt.event.ActionEvent evt){
        CadastroLivros cadastroLivros = new CadastroLivros();
        cadastroLivros.setVisible(true);
        dispose();
    }

    private void botaoEmprestarPerformed(java.awt.event.ActionEvent evt){
        try {
            String titulo = textField1.getText();
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Informe o título do livro.");
                return;
            }

            LivroDAO livro = new LivroDAO();

            EmprestimoController controller = new EmprestimoController();
            controller.realizarEmprestimo(UsuarioAtivo.getUsuarioAtivo(), livro.encontrarLivro(titulo));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "PrincipalView" + e);
        }
    }
    private void botaoDevolucaoPerformed(java.awt.event.ActionEvent evt){
        try {
            String titulo = textField1.getText();
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Informe o título do livro.");
                return;
            }

            LivroDAO livro = new LivroDAO();

            EmprestimoController controller = new EmprestimoController();
            controller.realizarDevolucao(UsuarioAtivo.getUsuarioAtivo(), livro.encontrarLivro(titulo));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "PrincipalWiew" + e);
        }
    }

    public void montarTabela(){
        try {
            LivroDAO livros = new LivroDAO();
            ResultSet acervo = livros.todosLivros();
            DefaultTableModel dados = (DefaultTableModel) tabelaLivros.getModel();

            while(acervo.next()){
                String titulo = acervo.getString("titulo");
                String autor = acervo.getString("autor");
                String categoria = acervo.getString("categoria");
                int ano = acervo.getInt("ano_publicacao");
                int total = acervo.getInt("numero_exemplares");
                int alugados = acervo.getInt("numero_emprestados");
                int disponiveis = total - alugados;


                dados.addRow(new Object[]{titulo, autor, categoria, ano, disponiveis});
            }
        } catch (SQLException e) {
            throw new RuntimeException("erro no montartabela90" + e.getMessage());
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
