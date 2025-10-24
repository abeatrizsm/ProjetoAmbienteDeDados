package Controller;

import Service.AuthService;

public class AuthController {
    public boolean autenticarLogin(String user, String senha){
        AuthService authService = new AuthService();
        boolean autenticado = authService.autenticarLogin(user,senha);
        return autenticado;
    }
}
