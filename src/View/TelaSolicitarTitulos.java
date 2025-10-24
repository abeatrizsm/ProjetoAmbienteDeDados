package View;

import Controller.LivroController;

import javax.swing.*;

public class TelaSolicitarTitulos extends JFrame {
    private JTextField inserirAno;
    private JTextField inserirAutor;
    private JTextField inserirCategoria;
    private JTextField inserirTitulo;
    private JButton adicionarButton;
    private JTextField inserirNExemplares;
    private JPanel painelCL;
    private JLabel label;
    private JButton voltar;

    public TelaSolicitarTitulos(){
        setTitle("Cadastro Livros");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);



        setVisible(true);
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
}
