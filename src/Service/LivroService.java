package Service;

import DAO.ConexaoDAO;
import DAO.LivroDAO;
import DAO.RequisicoesDAO;
import DTO.LivrosDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivroService {
    Connection conn;
    LivroDAO livro = new LivroDAO();
    RequisicoesDAO requisicoes = new RequisicoesDAO();

    public boolean cadastrarLivro(String titulo, String autor, String ano, String categoria, String nExemplares){
        if (livro.encontrarLivro(titulo).getTitulo() != null){
            JOptionPane.showMessageDialog(null, "O livro já existe no banco.");
            return false;
        }
        else {
            if (livro.cadastrarLivro(titulo, autor, ano, categoria, nExemplares ) > 0){
                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                return true;
            }
            return false;
        }
    }
    public ResultSet todosLivros(){
        return livro.todosLivros();
    }

    public ResultSet buscarLivros(String titulo){
        if (livro.encontrarLivro(titulo).getTitulo() == null){
            JOptionPane.showMessageDialog(null, "O livro não existe no banco.");
            return null;

        }
        else {
            return livro.buscarLivros(titulo);
        }
    }
    public boolean apagarLivro(String titulo, String autor){
        if (livro.encontrarLivro(titulo).getNumero_emprestados() == 0){
            if (livro.encontrarLivro(titulo).getTitulo() == null){
                JOptionPane.showMessageDialog(null, "O livro não existe no banco.");
                return false;
            }
            else {
                if (livro.apagarLivro(titulo, autor) > 0){
                    JOptionPane.showMessageDialog(null, "Livro apagado com sucesso!");
                    return true;
                }
                return false;
            }
        }else {
            JOptionPane.showMessageDialog(null, "Não é possivel apagar um livro que possui exemplares emprestados.");
            return false;
        }

    }

    public ResultSet mostrarLivrosCategoria(String categoria){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "select titulo, autor, categoria, ano_publicacao, numero_exemplares, numero_emprestados from livros where categoria = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, categoria);
            return preparar.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Erro na classe livroService - metodo: mostrarLivrosCategoria + erro: " + e.getMessage());
            return null;
        }
    }
    public LivrosDTO encontrarLivro(String titulo)  {
        return livro.encontrarLivro(titulo);
    }



}
