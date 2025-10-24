package Controller;

import DTO.LivrosDTO;
import Service.LivroService;

import java.sql.ResultSet;

public class LivroController {
    LivroService livroService = new LivroService();
    public boolean realizarEmprestimo(String titulo, String autor, String ano, String categoria, String nExemplares){
        boolean realizado = livroService.cadastrarLivro(titulo, autor, ano, categoria, nExemplares);
        return realizado;
    }

    public ResultSet buscarLivro(String titulo){
        return livroService.buscarLivros(titulo);
    }

    public boolean apagarLivro(String titulo, String autor){
        boolean apagado = livroService.apagarLivro(titulo, autor);
        return apagado;
    }
    public boolean cadastrarLivro(String titulo, String autor, String ano, String categoria, String nExemplares){
        boolean realizado = livroService.cadastrarLivro(titulo, autor,ano,categoria,nExemplares);
        return realizado;
    }
    public ResultSet buscarLivros(String titulo){
        return livroService.buscarLivros(titulo);
    }
    public ResultSet todosLivros(){
        return livroService.todosLivros();
    }
    public LivrosDTO encontrarLivro(String titulo)  {
        return livroService.encontrarLivro(titulo);
    }

}
