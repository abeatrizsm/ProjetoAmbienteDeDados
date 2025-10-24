package DAO;

import DTO.EmprestimosDTO;
import DTO.LivrosDTO;
import DTO.UsuarioDTO;
import Globals.UsuarioAtivo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmprestimosDAO {
    Connection conn;


    public int realizarEmprestimo(UsuarioDTO usuario, LivrosDTO livro){
        conn = new ConexaoDAO().conectaBD();
        LocalDate dataEmprestimo = LocalDate.now();

        try {
            String sql = "insert into emprestimos(data_retirada, situacao, id_livro, id_usuario) values (?, 'Em emprestimo', ?, ?)";

            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, String.valueOf(dataEmprestimo));
            preparar.setInt(2, livro.getId_livro());
            preparar.setInt(3, usuario.getIdUsuario());

            int nLinhas = preparar.executeUpdate();

            if (nLinhas > 0){
                sql = "update livros set numero_emprestados = numero_emprestados + 1 where id_livro = ?";
                preparar = conn.prepareStatement(sql);
                preparar.setInt(1, livro.getId_livro());
                preparar.executeUpdate();
            }
            return nLinhas;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro no emprestimo service" + e.getMessage());
            return 0;
        }
    }

    public int realizarDevolucao(UsuarioDTO usuario, LivrosDTO livro){
        conn = new ConexaoDAO().conectaBD();
        LocalDate dataDevolucao = LocalDate.now();

        try {
            String sql = "update emprestimos set situacao = 'Devolvido', data_devolucao = ? where id_usuario = ? and id_livro = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setDate(1, java.sql.Date.valueOf(dataDevolucao));
            preparar.setInt(2, usuario.getIdUsuario());
            preparar.setInt(3, livro.getId_livro());


            int nLinhas = preparar.executeUpdate();

            if (nLinhas>0){
                sql = "update livros set numero_emprestados = numero_emprestados - 1 where id_livro = ?";
                preparar = conn.prepareStatement(sql);
                preparar.setInt(1, livro.getId_livro());
                preparar.executeUpdate();
            }
            return nLinhas;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na devolucao Service" + e);
            return 0;
        }
    }
    public boolean livroEmprestado(LivrosDTO livro) {
        Connection conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "select count(*) from emprestimos where id_usuario = ? and id_livro = ? and situacao = 'Em emprestimo'";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setInt(1, UsuarioAtivo.getUsuarioAtivo().getIdUsuario());
            preparar.setInt(2, livro.getId_livro());

            ResultSet resultado = preparar.executeQuery();

            if (resultado.next()){
                int nlivros = resultado.getInt(1);
                return nlivros > 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO getLivrosEmprestados" + e.getMessage());
        }
        return false;
    }

    public int multa(float multa, LivrosDTO livro, UsuarioDTO usuario){
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "update emprestimos set multa = ? where id_usuario = ? and id_livro = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setFloat(1, multa);
            preparar.setInt(2, usuario.getIdUsuario());
            preparar.setInt(3, livro.getId_livro());

            int linhas = preparar.executeUpdate();
            return linhas;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ResultSet puxarEmprestimos(){
        conn = new  ConexaoDAO().conectaBD();

        try {
            String sql = "select u.nome, u.matricula, l.titulo, e.data_retirada, e.data_devolucao, e.multa, e.situacao from emprestimos e join usuarios u on u.id_usuario = e.id_usuario join livros l on l.id_livro = e.id_livro;";
            PreparedStatement preparar = conn.prepareStatement(sql);
            ResultSet emprestimos = preparar.executeQuery();

            if (emprestimos.next()){
                return emprestimos;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao puxar os emprestimos empretimosDAO " + e.getMessage());
        }
        return null;
    }
    public ResultSet puxarEmprestimosEmAndamento(){
        conn = new  ConexaoDAO().conectaBD();

        try {
            String sql = "select u.nome, u.matricula, l.titulo, e.data_retirada, e.data_devolucao, e.multa, e.situacao from emprestimos e join usuarios u on u.id_usuario = e.id_usuario join livros l on l.id_livro = e.id_livro where e.situacao = 'Em emprestimo';";
            PreparedStatement preparar = conn.prepareStatement(sql);
            ResultSet emprestimos = preparar.executeQuery();

            return emprestimos;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao puxar os emprestimos empretimosDAO " + e.getMessage());
        }
        return null;
    }

    public ResultSet puxarEmprestimosAluno(int matricula){
        conn = new  ConexaoDAO().conectaBD();

        try {
            String sql = "select u.nome, u.matricula, l.titulo, e.data_retirada, e.data_devolucao, e.multa, e.situacao from emprestimos e join usuarios u on u.id_usuario = e.id_usuario join livros l on l.id_livro = e.id_livro where u.matricula = ?;";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setInt(1, matricula);
            ResultSet emprestimos = preparar.executeQuery();

            return emprestimos;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao puxar os emprestimos do aluno empretimosDAO " + e.getMessage());
        }
        return null;
    }

    public ResultSet podeAlugar(String matricula){
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "select count(e.id_emprestimo) from emprestimos e join usuarios u on u.id_usuario = e.id_usuario where u.matricula = ? and e.situacao = Em andamento";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, matricula);
            return preparar.executeQuery();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro no podeAlugar - emprestimoDAO. ERRO: " + e.getMessage());
            return null;
        }
    }

}
// controller
//if (alunos == null) {
//        JOptionPane.showMessageDialog(null, "Nenhum empréstimo encontrado para este aluno.");
//                return;
//                        }

