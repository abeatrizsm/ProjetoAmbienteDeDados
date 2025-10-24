package Service;

import DAO.LivroDAO;
import DAO.RequisicoesDAO;

import javax.swing.*;
import java.sql.Connection;

public class RequisicoesService {
        Connection conn;
        LivroDAO livro = new LivroDAO();
        RequisicoesDAO requisicoes = new RequisicoesDAO();

    public boolean cadastrarRequisicao(String titulo, String autor, String ano){

        if (livro.encontrarLivro(titulo).getTitulo() != null){
            JOptionPane.showMessageDialog(null, "O livro já existe no banco.");
            return false;
        }
        else {
            if (requisicoes.cadastrarRequisicao(titulo, autor, ano) > 0){
                JOptionPane.showMessageDialog(null, "Solicitacao realizada com sucesso!");
                return true;
            }
            return false;
        }
    }
    public boolean apagarRequisicao(String titulo, String autor, String ano){

        if (livro.encontrarLivro(titulo).getTitulo() == null){
            JOptionPane.showMessageDialog(null, "O livro não existe no banco.");
            return false;
        }
        else {
            if (requisicoes.apagarRequisicao(titulo, autor, ano) > 0){
                return true;
            }
            return false;
        }
    }
}
