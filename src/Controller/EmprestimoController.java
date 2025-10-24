package Controller;

import DAO.EmprestimosDAO;
import DTO.EmprestimosDTO;
import DTO.LivrosDTO;
import DTO.UsuarioDTO;
import Service.EmprestimoService;

import java.sql.ResultSet;

public class EmprestimoController {
    EmprestimoService emprestimoService = new EmprestimoService();

    public boolean realizarEmprestimo(UsuarioDTO usuario, LivrosDTO livro){
        boolean realizado = emprestimoService.realizarEmprestimo(usuario, livro);
        return realizado;
    }

    public boolean realizarDevolucao(UsuarioDTO usuario, LivrosDTO livro){
        boolean efetuado = emprestimoService.realizarDevolucao(usuario,livro);
        return efetuado;
    }
    public ResultSet puxarEmprestimosEmAndamento(){

        return emprestimoService.puxarEmprestimosEmAndamento();
    }

}
