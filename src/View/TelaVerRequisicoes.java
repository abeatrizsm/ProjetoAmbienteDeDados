package View;

import Controller.LivroController;
import Controller.RequisicoesController;
import DAO.EmprestimosDAO;
import DAO.LivroDAO;
import DAO.RequisicoesDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaVerRequisicoes extends JFrame {

    private JTable tabelaLivros;
    private JTextField campoExemplares;
    private JTextField campoCategoria;
    private JButton botaoRecusar;
    private JButton botaoAdicionar;
    private JButton botaoVoltar;
    private JLabel labelTitulo;

    public TelaVerRequisicoes() {

        setTitle("Ver requisicoes");
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

        labelTitulo = new JLabel("  Ver requisicoes");
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

        JPanel textPanels = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel panelLabels = new JPanel(new GridLayout(1, 3, 50, 5));
        panelLabels.setBackground(branco);
        JLabel labelExemplares = new JLabel("Digite o numero de exemplares:");
        JLabel labelCategoria = new JLabel("Digite a categoria:");
        JLabel elementoNulo = new JLabel(" ");
        labelExemplares.setFont(fonteLabel);
        labelCategoria.setFont(fonteLabel);


        campoExemplares = new JTextField();
        campoExemplares.setFont(fonteDefault);


        campoCategoria = new JTextField();
        campoCategoria.setFont(fonteDefault);

        panelLabels.add(labelExemplares);
        panelLabels.add(labelCategoria);
        panelLabels.add(elementoNulo);
//        panelLabels.add(elementoNulo);
//        panelLabels.add(elementoNulo);

        textPanels.add(campoExemplares);
        textPanels.add(campoCategoria);
        textPanels.setBackground(branco);


        JPanel botoesBusca = new JPanel(new GridLayout(1, 2, 10, 0));
        botoesBusca.setBackground(branco);

        botaoRecusar = new JButton("Recusar");
        botaoRecusar.setFont(fonteBotao);
        botaoRecusar.setBackground(azulMedio);
        botaoRecusar.setForeground(branco);
        botaoRecusar.setPreferredSize(tamanhoBotao);
        botaoRecusar.setFocusPainted(false);

        botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.setFont(fonteBotao);
        botaoAdicionar.setBackground(azulMedio);
        botaoAdicionar.setForeground(branco);
        botaoAdicionar.setPreferredSize(tamanhoBotao);
        botaoAdicionar.setFocusPainted(false);


        botoesBusca.add(botaoRecusar);
        botoesBusca.add(botaoAdicionar);

        painelBusca.add(panelLabels, BorderLayout.NORTH);
        painelBusca.add(textPanels, BorderLayout.CENTER);
        painelBusca.add(botoesBusca, BorderLayout.EAST);


        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"Título", "Autor", "Ano"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaLivros = new JTable(modeloTabela);
        tabelaLivros.setFont(fonteDefault);
        tabelaLivros.setRowHeight(28);
        tabelaLivros.getTableHeader().setFont(fonteDefault);
        tabelaLivros.setGridColor(grade);
        tabelaLivros.setFillsViewportHeight(true);

        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabelaLivros.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabelaLivros.getColumnModel().getColumn(2).setPreferredWidth(150);

        montarTabela();

        JScrollPane scroll = new JScrollPane(tabelaLivros);
        scroll.setBorder(BorderFactory.createCompoundBorder(bordaTabela, bordaPreta));
        scroll.setBackground(branco);

        painelPrincipal.add(scroll, BorderLayout.CENTER);
        add(painelPrincipal, BorderLayout.CENTER);

        setVisible(true);

        botaoVoltar.addActionListener(evt -> voltarTelaPrincipal(evt));
        botaoAdicionar.addActionListener(evt -> botaoAdicionarPerformed(evt));
        botaoRecusar.addActionListener(evt -> botaoRecusarPerformed(evt));
    }

    public void botaoAdicionarPerformed(java.awt.event.ActionEvent evt) {

        try {
            String exemplares = campoExemplares.getText();
            String categoria = campoCategoria.getText();
            if (exemplares.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todas as informacoes.");
                return;
            }

            int linha = tabelaLivros.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma sugestao.");
                return;
            }

            if (!categoria.matches("[A-Za-zÀ-ÿ\\s]+")) {
                JOptionPane.showMessageDialog(null, "A categoria deve conter apenas letras.");
                return;
            }

            int nExemplares = Integer.parseInt(exemplares);

            if (nExemplares <= 0) {
                JOptionPane.showMessageDialog(null, "O número de exemplares deve ser maior que zero.");
                return;
            }

            String titulo = tabelaLivros.getValueAt(linha, 0).toString();
            String autor = tabelaLivros.getValueAt(linha, 1).toString();
            String ano = tabelaLivros.getValueAt(linha, 2).toString();


            LivroController livro = new LivroController();
            livro.cadastrarLivro(titulo, autor, ano, categoria, exemplares);
            RequisicoesController controller = new RequisicoesController();
            controller.apagarRequisicao(titulo, autor, ano);
            montarTabela();


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro no botao adicionar - tela ver requisicoes + erro : " + e.getMessage());
        }
    }//3025021

    public void botaoRecusarPerformed(java.awt.event.ActionEvent evt) {
        try {
            int linha = tabelaLivros.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma sugestao.");
                return;
            }
            String titulo = tabelaLivros.getValueAt(linha, 0).toString();
            String autor = tabelaLivros.getValueAt(linha, 1).toString();
            String ano = tabelaLivros.getValueAt(linha, 2).toString();

            RequisicoesController controller = new RequisicoesController();
            boolean realizado = controller.apagarRequisicao(titulo, autor, ano);
            if (realizado) {
                JOptionPane.showMessageDialog(null, "Livro apagado com sucesso.");
            }
            montarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro no botão Recusar - Tela Ver Requisições: " + e.getMessage());
        }

    }

    public void montarTabela() {
        try {
            // adicionar service e controller
            RequisicoesDAO empretimos = new RequisicoesDAO();
            ResultSet aquisicoes = empretimos.puxarRequisicoes();
            DefaultTableModel dados = (DefaultTableModel) tabelaLivros.getModel();

            dados.setRowCount(0);


            while (aquisicoes.next()) {
                String titulo = aquisicoes.getString("titulo");
                String autor = aquisicoes.getString("autor");
                int ano = aquisicoes.getInt("ano_publicacao");

                dados.addRow(new Object[]{titulo, autor, ano});
            }

        } catch (SQLException e) {
            throw new RuntimeException("erro no montartabela()" + e.getMessage());
        }
    }

    private void voltarTelaPrincipal(java.awt.event.ActionEvent evt) {
        TelaPrincipalAdm tela = new TelaPrincipalAdm();
        tela.setVisible(true);
        dispose();

    }

}