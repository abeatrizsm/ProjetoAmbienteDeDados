package DAO;

import DTO.LivrosDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivroDAO {
    Connection conn;

    public LivrosDTO encontrarLivro(String titulo)  {
        conn = new ConexaoDAO().conectaBD();
        LivrosDTO livro = new LivrosDTO();

        try {
            String sql = "select * from livros where titulo COLLATE utf8mb4_general_ci = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, titulo);

            ResultSet result = preparar.executeQuery();

            if (result.next()){
                livro.setId_livro(result.getInt("id_livro"));
                livro.setTitulo(result.getString("titulo"));
                livro.setAutor(result.getString("autor"));
                livro.setAno_publicacao(result.getInt("ano_publicacao"));
                livro.setCategoria(result.getString("categoria"));
                livro.setNumero_emprestados(result.getInt("numero_emprestados"));
                livro.setNumero_exemplares(result.getInt("numero_exemplares"));
                livro.setExemplaresDisponiveis(livro.getNumero_exemplares() - livro.getNumero_emprestados());
            }
            return livro;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"O erro foi no metodo encontrarLivro() - classe livroDAO. Erro: " + e.getMessage());
            return null;
        }
    }
    //fazer categorias

    public ResultSet buscarLivros(String titulo)  {
        conn = new ConexaoDAO().conectaBD();
        LivrosDTO livro = new LivrosDTO();

        try {
            String sql = "select * from livros where titulo COLLATE utf8mb4_general_ci = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, titulo);
            ResultSet result = preparar.executeQuery();

            return result;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"O erro foi no metodo buscarLivros() - classe LIVRODAO . Erro: " + e.getMessage());
            return null;
        }
    }

    public ResultSet todosLivros(){
        conn = new ConexaoDAO().conectaBD();
        try{
            String sql = "select titulo, autor, categoria, ano_publicacao, numero_exemplares, numero_emprestados from livros order by titulo";
            PreparedStatement preparar = conn.prepareStatement(sql);
            return preparar.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "O erro foi no metododo todosLivros() - classe LivroDAO. Erro: " + e.getMessage());
            return null;
        }
    }

    public int cadastrarLivro(String titulo, String autor, String ano, String categoria, String nExemplares){
        conn = new ConexaoDAO().conectaBD();
        try{
            String sql = "insert into livros(titulo, autor, ano_publicacao, categoria, numero_exemplares) values (?,?,?,?,?)";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, titulo);
            preparar.setString(2, autor);
            preparar.setString(3, ano);
            preparar.setString(4, categoria);
            preparar.setString(5, nExemplares);

            int linhas = preparar.executeUpdate();
            return linhas;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro no cadastro de livros - classe LivroDAO. Erro: " + e.getMessage());
            return 0;
        }
    }
    public int apagarLivro(String titulo, String autor){
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "delete from livros where titulo = ? and autor = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, titulo);
            preparar.setString(2, autor);

            int linhas = preparar.executeUpdate();
            return linhas;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro na classe LivroDAO - metodo: apagarLivro(). Erro: " + e.getMessage());
            return 0;
        }
    }
    public int atualizarLivro(int numExemplares, String titulo, String autor){
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "update livros set numero_exemplares = ? where titulo = ? and autor = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setInt(1, numExemplares);
            preparar.setString(2, titulo);
            preparar.setString(3, autor);

            int linhas = preparar.executeUpdate();
            return linhas;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro na classe LivroDAO - metodo: atualizarLivro(). Erro: " + e.getMessage());
            return 0;
        }
    }


}
// int quantidade = acervo.getInt("(numero_exemplares - numero_emprestados)");, "Disponíveis"