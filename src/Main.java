import View.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           login tela = new login();
           //TelaLogin tela = new TelaLogin();
//TelaPrincipalAdm tela = new TelaPrincipalAdm();
//            Principal tela = new Principal();
           //CadastroLivros tela = new CadastroLivros();
           // TelaPrincipalUsuario tela = new TelaPrincipalUsuario();
           tela.setVisible(true);
        });
    }

}