package Globals;

import DTO.UsuarioDTO;

public class UsuarioAtivo {
    private static UsuarioDTO usuarioAtivo;

    public static UsuarioDTO getUsuarioAtivo() {
        return usuarioAtivo;
    }

    public static void setUsuarioAtivo(UsuarioDTO usuarioAtivo) {
        UsuarioAtivo.usuarioAtivo = usuarioAtivo;
    }

    public static void sair(){
        usuarioAtivo = null;
    }
}
