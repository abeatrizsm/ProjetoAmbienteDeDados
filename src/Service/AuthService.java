package Service;

import DAO.UsuarioDAO;
import DTO.UsuarioDTO;
import Globals.UsuarioAtivo;

public class AuthService {
    public boolean autenticarLogin(String user, String senha){
        if (user.isEmpty() || senha.isEmpty()){
            return false;
        }

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setMatricula(user);
        usuario.setEmail(user);
        usuario.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioDTO autenticado = usuarioDAO.autenticacaoUsuario(usuario);
        if (autenticado != null && !autenticado.getTipo().equals("Admin")){
            UsuarioAtivo.setUsuarioAtivo(autenticado);
            return true;
        }
        else {
            return false;
        }
    }
}
