package DAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {
    public Connection conectaBD(){
       Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BibliotecaUniversitaria", "root", "2410411"); // gerencia,ento do drive
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return conn;

    }

}


