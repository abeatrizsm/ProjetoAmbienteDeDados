package DAO;

import DTO.UsuarioDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    Connection conn;

    public UsuarioDTO autenticacaoUsuario(UsuarioDTO usuario){
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "select * from usuarios where (matricula = ? or email = ?) and binary senha = ? ";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, usuario.getMatricula());
            preparar.setString(2, usuario.getEmail());
            preparar.setString(3, usuario.getSenha());

            ResultSet result = preparar.executeQuery();
            if (result.next()){
                UsuarioDTO usuarioValido = new UsuarioDTO();
                usuarioValido.setIdUsuario(result.getInt("id_usuario"));
                usuarioValido.setNome(result.getString("nome"));
                usuarioValido.setMatricula(result.getString("matricula"));
                usuarioValido.setEmail(result.getString("email"));
                usuarioValido.setTipo(result.getString("tipo"));
                return usuarioValido;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO " + e.getMessage());
            return null;
        }
    }

    public UsuarioDTO encontrarUsuario(String matricula){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "select nome, email, matricula, tipo, id_usuario from usuarios where matricula = ?; ";
            PreparedStatement preparar = conn.prepareStatement(sql);
            preparar.setString(1, matricula);
            ResultSet usuario = preparar.executeQuery();

            UsuarioDTO usuarioDTO = new UsuarioDTO();

            if (usuario.next()){
                usuarioDTO.setNome(usuario.getString("nome"));
                usuarioDTO.setEmail(usuario.getString("email"));
                usuarioDTO.setMatricula(usuario.getString("matricula"));
                usuarioDTO.setTipo(usuario.getString("tipo"));
                usuarioDTO.setIdUsuario(usuario.getInt("id_usuario"));

                return usuarioDTO;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro em encontrar usuario USUARIODAO " + e.getMessage());
        }
        return null;
    }
    public void atualizarAlugados(){

    }

}


