package Service;

import DAO.ConexaoDAO;
import DAO.EmprestimosDAO;
import DTO.EmprestimosDTO;
import DTO.LivrosDTO;
import DTO.UsuarioDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmprestimoService {
    Connection conn;
    LocalDate data = LocalDate.now();
    LocalDate dataDevolucao = data.plusDays(14);
    EmprestimosDAO emprestimosDAO = new EmprestimosDAO();


    public ResultSet puxarEmprestimosEmAndamento(){
        return emprestimosDAO.puxarEmprestimosEmAndamento();
    }

    public boolean podeAlugar(String matricula){
        try {
            ResultSet resultado = emprestimosDAO.podeAlugar(matricula);

            if (resultado.next()){
                int numero = resultado.getInt(0);
                if (numero<3){
                    return true;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro no podeAlugar() classe emprestimoService. Erro: " + e.getMessage());
        }
        return false;

    }


    public boolean realizarEmprestimo(UsuarioDTO usuario, LivrosDTO livro) {



        if (emprestimosDAO.livroEmprestado(livro)) {
            JOptionPane.showMessageDialog(null, "Esse livro ja esta alugado por você.");
            return false;
        }
        if (livro.getExemplaresDisponiveis() >= 1) {

            int linhas = emprestimosDAO.realizarEmprestimo(usuario, livro);

            if (linhas > 0) {
                livro.livroEmprestado();
                JOptionPane.showMessageDialog(null, "Emprestimo realizado com sucesso. O prazo para devolução é de 14 dias.\n Data de retirada: " + data + "| Data de devolução: " + dataDevolucao);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Falha no registro do empréstimo.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Esse livro está com todos os exemplares alugados");
            return false;
        }

    }

    public boolean realizarDevolucao(UsuarioDTO usuario, LivrosDTO livro) {

        LocalDate data = LocalDate.now();
        float taxaDiaria = 0.5F;
        float multa = 0;

        if (emprestimosDAO.livroEmprestado(livro)) {
            try {
                conn = new ConexaoDAO().conectaBD();
                String sql = "select data_retirada from emprestimos where id_usuario = ? and id_livro = ?";
                PreparedStatement preparar = conn.prepareStatement(sql);
                preparar.setInt(1, usuario.getIdUsuario());
                preparar.setInt(2, livro.getId_livro());

                ResultSet resultado = preparar.executeQuery();

                if (resultado.next()) {
                    LocalDate dataRetirada = LocalDate.parse(resultado.getString("data_retirada"));
                    LocalDate dataDevolucao = dataRetirada.plusDays(14);
                    long quantDias = ChronoUnit.DAYS.between(dataDevolucao, data);

                    if (data.isAfter(dataDevolucao)) {
                        multa = taxaDiaria * quantDias;
                    }

                    emprestimosDAO.multa(multa, livro, usuario);

                    int linhas = emprestimosDAO.realizarDevolucao(usuario, livro);

                    if (linhas > 0) {
                        if (multa > 0) {
                            JOptionPane.showMessageDialog(null, "Devolucão efetuada com sucesso. Você foi multado no valor de : " + multa + " reais.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Devolucão efetuada com sucesso.");

                        }
                        emprestimosDAO.multa(multa, livro, usuario);
                        return true;

                    } else {
                        JOptionPane.showMessageDialog(null, "Falha no registro da devolução.");
                        return false;
                    }

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Esse livro foi alugado pelo Aluno.");
            return false;
        }
        return false;
    }
}
