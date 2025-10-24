package Controller;

import Service.RequisicoesService;

public class RequisicoesController {
    RequisicoesService service = new RequisicoesService();
    public boolean realizarRequisicao(String titulo, String autor, String ano){
        boolean realizado = service.cadastrarRequisicao(titulo, autor, ano);
        return realizado;
    }
    public boolean apagarRequisicao(String titulo, String autor, String ano){
        boolean realizado = service.apagarRequisicao(titulo, autor, ano);
        return realizado;
    }
}
