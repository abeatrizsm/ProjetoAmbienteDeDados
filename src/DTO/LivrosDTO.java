package DTO;

public class LivrosDTO {
    int id_livro;
    String titulo;
    String autor;
    int ano_publicacao;
    String categoria;
    int numero_exemplares;
    int numero_emprestados;
    int exemplaresDisponiveis;

    public int getNumero_exemplares() {
        return numero_exemplares;
    }

    public void setNumero_exemplares(int numero_exemplares) {
        this.numero_exemplares = numero_exemplares;
    }

    public int getNumero_emprestados() {
        return numero_emprestados;
    }

    public void setNumero_emprestados(int numero_emprestados) {
        this.numero_emprestados = numero_emprestados;
    }

    public void livroEmprestado(){
        exemplaresDisponiveis -= 1;
    }

    public void livroDevolvido(){
        exemplaresDisponiveis += 1;
    }

    public int getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }

    public void setExemplaresDisponiveis(int exemplaresDisponiveis) {
        this.exemplaresDisponiveis = numero_exemplares - numero_emprestados;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno_publicacao() {
        return ano_publicacao;
    }

    public void setAno_publicacao(int ano_publicacao) {
        this.ano_publicacao = ano_publicacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }
}
