package View;

import Controller.AuthController;
import Globals.UsuarioAtivo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;


public class login extends JFrame{

    private JPasswordField campoSenha;
    private JPanel principal;
    private JPanel jpanelBackgrond;
    private JPanel jpanelInfos;
    private JTextField campoLogin;
    private JButton botaoLogin;
    private JLabel labelSenha;
    private JLabel labelUsuario;
    private JLabel labelSubtitulo;
    private JLabel labelTitulo;


    public login() {
        setContentPane(principal);
        setTitle("Login");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        Font fonteDefault = new Font("Segoe UI", Font.PLAIN, 18);
        Font fonteDesc = new Font("Segoe UI", Font.PLAIN, 18);
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 30);
        Color corTitulo = new Color(29, 47, 82);

        labelTitulo.setFont(fonteTitulo);
        labelTitulo.setForeground(corTitulo);
        labelSubtitulo.setFont(fonteDefault);
        //labelSubtitulo.setFont(fon);
        labelUsuario.setFont(fonteDefault);
        labelSenha.setFont(fonteDefault);
        campoLogin.setFont(fonteDefault);
        campoLogin.setPreferredSize(new Dimension(100, 40));
        campoSenha.setFont(fonteDefault);
        campoSenha.setPreferredSize(new Dimension(100, 40));
        botaoLogin.setPreferredSize(new Dimension(100, 40));
        botaoLogin.setFont(fonteDefault);
        jpanelBackgrond.setBorder(new EmptyBorder(50, 50,50,50));
        jpanelInfos.setBorder(new EmptyBorder(20, 50,30,50));
        botaoLogin.addActionListener(evt -> botaoLoginPerformed(evt));

    }


    private void botaoLoginPerformed(java.awt.event.ActionEvent evt){
        try {
            String usuario = campoLogin.getText();
            String senhaUsuario = new String(campoSenha.getPassword());

            AuthController controller = new AuthController();
            boolean autenticado = controller.autenticarLogin(usuario, senhaUsuario);

            if (autenticado){
                if (Objects.equals(UsuarioAtivo.getUsuarioAtivo().getTipo(), "admin")){
                    TelaPrincipalAdm tela = new TelaPrincipalAdm();
                    tela.setVisible(true);
                    dispose();
                }
                else {
                    TelaPrincipalUsuario telaPrincipal = new TelaPrincipalUsuario();
                    telaPrincipal.setVisible(true);
                    dispose();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "loginView Tela de login problema " + e);
        }
    }
}
