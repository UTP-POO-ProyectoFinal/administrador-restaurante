package com.mycompany.poo_proyecto;

import com.mycompany.poo_proyecto.view.Login;
import java.io.*;
import javax.swing.*;

public class POO_Proyecto extends JFrame {

    private static final int resolutionW = 450;
    private static final int resolutionH = 800;

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POO_Proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Login login = new Login();
        
        login.setVisible(true);
    }
    
    
}
