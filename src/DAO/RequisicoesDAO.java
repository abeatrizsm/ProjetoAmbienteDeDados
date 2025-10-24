package DAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequisicoesDAO {
    Connection conn;

    public ResultSet puxarRequisicoes(){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "select * from livrosRequisitados";
            PreparedStatement preparar = conn.prepareStatement(sql);
            ResultSet resultado = preparar.executeQuery();
            return resultado;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro no puxar requisicoes");
            return null;
        }

    }

    public int cadastrarRequisicao(String titulo, String autor, String ano){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "insert into livrosRequisitados(titulo, autor, ano_publicacao) values (?,?,?)";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, titulo);
            preparar.setString(2, autor);
            preparar.setString(3, ano);

            int linhas = preparar.executeUpdate();
            return linhas;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar requisicao");
            return 0;
        }
    }
    public int apagarRequisicao(String titulo, String autor, String ano){
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "delete from livrosrequisitados where titulo = ? and autor = ? and ano_publicacao = ?";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, titulo);
            preparar.setString(2, autor);
            preparar.setString(3, ano);

            int linhas = preparar.executeUpdate();
            return linhas;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro na classe RequisicaoDAO - metodo: apagarLivro(). Erro: " + e.getMessage());
            return 0;
        }
    }
}
