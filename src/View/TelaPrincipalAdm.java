package View;

import Controller.EmprestimoController;
import DAO.EmprestimosDAO;
import DAO.LivroDAO;
import DAO.UsuarioDAO;
import Globals.UsuarioAtivo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaPrincipalAdm extends JFrame{
    private JTable tabelaLivros;
    private JTextField barraBuscar;
    private JButton botaoBuscar;
    private JButton botaoDevolver;
    private JButton botaoRequisicao;
    private JButton botaoGerenciar;
    private JButton botaoVerRequisicoes;
    private JLabel label;
    private JLabel labelAlugar;
    private JPanel panel1;

    public TelaPrincipalAdm(){
        setTitle("Biblioteca Universitária");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Color branco = new Color(255, 255, 255);
        Color fundo = new Color(242, 245, 246);
        Font fonteDefault = new Font("Segoe UI", Font.PLAIN, 16);
        Color corBotao = new Color(39, 84, 133);
        Color barraCor = new Color(45, 95, 150);
        Color topoTabela = new Color(75, 130, 180, 255);


        JPanel lateral = new JPanel();
        lateral.setBackground(barraCor);
        lateral.setLayout(new GridLayout(1,3,0,0));
        lateral.setBackground(new Color(235, 235, 235));

        botaoRequisicao = new JButton("Cadastrar");
        botaoGerenciar = new JButton("Gerenciar");
        botaoVerRequisicoes = new JButton("Ver Requisições");

        Font fonteBotao = new Font("Segoe UI", Font.PLAIN, 16);

        botaoRequisicao.setFont(fonteBotao);
        botaoGerenciar.setFont(fonteBotao);
        botaoVerRequisicoes.setFont(fonteBotao);
        botaoRequisicao.setForeground(branco);
        botaoGerenciar.setForeground(branco);
        botaoVerRequisicoes.setForeground(branco);

        botaoRequisicao.setBackground(corBotao);
        botaoGerenciar.setBackground(corBotao);
        botaoVerRequisicoes.setBackground(corBotao);

        lateral.add(botaoRequisicao);
        lateral.add(botaoGerenciar);
        lateral.add(botaoVerRequisicoes);

        add(lateral, BorderLayout.WEST);

        JPanel topo = new JPanel();

        topo.setLayout(new BorderLayout());
        topo.setBackground(barraCor);
        topo.setPreferredSize(new Dimension(1000, 60));

        EmptyBorder bordaTitulo = new EmptyBorder(0,20,0,200);

        label = new JLabel("  Biblioteca Universitária    ");
        Font titulo = new Font("Segoe UI", Font.BOLD, 22);
        label.setFont(titulo);
        label.setBorder(bordaTitulo);
        label.setForeground(branco);

        topo.add(label, BorderLayout.WEST);
        topo.add(lateral, BorderLayout.CENTER);
        add(topo, BorderLayout.NORTH);

        JPanel buscar = new JPanel();
        buscar.setLayout(new BorderLayout(20,10));
        buscar.setBackground(fundo);

        labelAlugar = new JLabel("Digite a matricula do aluno:");
        labelAlugar.setFont(new Font("Segoe UI", Font.BOLD, 16));

        barraBuscar = new JTextField();
        barraBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 0));
        botaoBuscar = new JButton("Buscar");
        botaoDevolver = new JButton("Devolver");

        botaoDevolver.setFont(fonteBotao);
        botaoBuscar.setFont(fonteBotao);
        botaoBuscar.setBackground(topoTabela);
        botaoDevolver.setBackground(topoTabela);
        botaoDevolver.setForeground(branco);
        botaoBuscar.setForeground(branco);
        botaoBuscar.setPreferredSize(new Dimension(120, 40));
        botaoDevolver.setPreferredSize(new Dimension(120, 40));

        botoes.add(botaoBuscar);
        botoes.add(botaoDevolver);
        botoes.setBackground(fundo);

        buscar.add(labelAlugar,BorderLayout.NORTH);
        buscar.add(barraBuscar,BorderLayout.CENTER);
        buscar.add(botoes,BorderLayout.EAST);

        EmptyBorder borda = new EmptyBorder(20,40,30,40);
        buscar.setBorder(borda);

        panel1 = new JPanel(new BorderLayout());
        panel1.add(buscar,BorderLayout.NORTH);

        String[] colunas = { "Aluno", "Matricula", "Livro", "Retirada", "Devolucao", "Multa","Situação"};
        DefaultTableModel tabelaDados = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede editar
            }
        };
        tabelaLivros = new JTable(tabelaDados);
        tabelaLivros.setModel(tabelaDados);
        tabelaLivros.setFont(fonteDefault);
        tabelaLivros.setRowHeight(28);
        tabelaLivros.getTableHeader().setFont(fonteDefault);
        tabelaLivros.setGridColor(new Color(180,180,180));
        tabelaLivros.setFillsViewportHeight(true);
        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabelaLivros.getColumnModel().getColumn(1).setPreferredWidth(70);
        tabelaLivros.getColumnModel().getColumn(2).setPreferredWidth(220);
        tabelaLivros.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabelaLivros.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaLivros.getColumnModel().getColumn(5).setPreferredWidth(40);
        tabelaLivros.getColumnModel().getColumn(6).setPreferredWidth(130);
        tabelaLivros.getTableHeader().setFont(fonteDefault);
        tabelaLivros.getTableHeader().setForeground(branco);
        tabelaLivros.getTableHeader().setBackground(topoTabela);

        JScrollPane scroll = new JScrollPane(tabelaLivros);
        LineBorder bordaCor = new LineBorder(Color.BLACK,1);
        scroll.setBorder(BorderFactory.createCompoundBorder(borda, bordaCor));
        scroll.setBackground(fundo);
        scroll.getVerticalScrollBar().setBackground(branco);

        panel1.add(scroll,BorderLayout.CENTER);
        add(panel1, BorderLayout.CENTER);

        montarTabela();

        botaoBuscar.addActionListener(evt -> botaoBuscarPerformed(evt));
        botaoDevolver.addActionListener(evt -> botaoDevolucaoPerformed(evt));
        botaoRequisicao.addActionListener(evt -> cadastrarLivrosPerformed(evt));
        botaoGerenciar.addActionListener(evt -> gerenciarPerformed(evt));
        botaoVerRequisicoes.addActionListener(evt -> verRequisicoesPerformed(evt));

    }
    private void verRequisicoesPerformed(java.awt.event.ActionEvent evt){
        TelaVerRequisicoes tela = new TelaVerRequisicoes();
        tela.setVisible(true);
        dispose();
    }


    private void cadastrarLivrosPerformed(java.awt.event.ActionEvent evt){
        CadastroLivros cadastrer = new CadastroLivros();
        cadastrer.setVisible(true);
        dispose();
    }
    private void gerenciarPerformed(java.awt.event.ActionEvent evt){
        TelaGerenciarLivros telaGerenciarLivros = new TelaGerenciarLivros();
        telaGerenciarLivros.setVisible(true);
        dispose();
    }

    private void botaoBuscarPerformed(java.awt.event.ActionEvent evt){
        try {
            String matricula = barraBuscar.getText();
            if (matricula.equals("")) {
                JOptionPane.showMessageDialog(null, "Informe a matricula do aluno.");
                montarTabela();
                return;
            }
            if (!matricula.matches("\\d{7}")) {
                JOptionPane.showMessageDialog(null, "A matrícula deve conter exatamente 7 números.");
                return;
            }

            int matriculaInt = Integer.parseInt(matricula);
            montarTabela(matriculaInt);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Botao buscar na PrincipalView: " + e);
        }
    }
    private void botaoDevolucaoPerformed(java.awt.event.ActionEvent evt){
        try {
            int linha = tabelaLivros.getSelectedRow();
            String titulo = tabelaLivros.getValueAt(linha,2).toString();
            String aluno =  tabelaLivros.getValueAt(linha, 1).toString();

            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione o título do livro.");
                return;
            }

            LivroDAO livro = new LivroDAO();
            UsuarioDAO usuario = new UsuarioDAO();
            UsuarioAtivo.setUsuarioAtivo(usuario.encontrarUsuario(aluno));

            EmprestimoController controller = new EmprestimoController();
            controller.realizarDevolucao(UsuarioAtivo.getUsuarioAtivo(), livro.encontrarLivro(titulo));
            montarTabela();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Botao devolucao na PrincipalWiew : " + e);
        }
    }

    public void montarTabela(){
        try {
            EmprestimoController controller = new EmprestimoController();
            ResultSet alunos = controller.puxarEmprestimosEmAndamento();
            DefaultTableModel dados = (DefaultTableModel) tabelaLivros.getModel();

            dados.setRowCount(0);


            while (alunos.next()){
                String nome = alunos.getString("nome");
                String matricula = alunos.getString("matricula");
                String titulo = alunos.getString("titulo");
                String data_retirada = alunos.getString("data_retirada");
                String data_devolucao = alunos.getString("data_devolucao");
                String multa = alunos.getString("multa");
                String situacao = alunos.getString("situacao");

                dados.addRow(new Object[]{nome, matricula, titulo, data_retirada, data_devolucao, multa, situacao});
            }

        }
        catch (SQLException e) {
            throw new RuntimeException("erro no montartabela()" + e.getMessage());
        }
    }
    public void montarTabela(int matricula){
        try {
            EmprestimosDAO empretimos = new EmprestimosDAO();
            ResultSet alunos = empretimos.puxarEmprestimosAluno(matricula);
            DefaultTableModel dados = (DefaultTableModel) tabelaLivros.getModel();

            dados.setRowCount(0);


            while (alunos.next()){
                String nome = alunos.getString("nome");
                String matriculaTabela = alunos.getString("matricula");
                String titulo = alunos.getString("titulo");
                String data_retirada = alunos.getString("data_retirada");
                String data_devolucao = alunos.getString("data_devolucao");
                String multa = alunos.getString("multa");
                String situacao = alunos.getString("situacao");

                dados.addRow(new Object[]{nome, matriculaTabela, titulo, data_retirada, data_devolucao, multa, situacao});
            }

        }
        catch (SQLException e) {
            throw new RuntimeException("erro no montartabela()" + e.getMessage());
        }
    }


}
