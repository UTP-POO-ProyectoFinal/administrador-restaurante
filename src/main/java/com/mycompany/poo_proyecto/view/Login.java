package com.mycompany.poo_proyecto.view;

import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame {

    private final int resolutionW = 800;
    private final int resolutionH = 450;

    JButton btnClose;

    public Login() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(resolutionH, resolutionW);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        btnClose = new JButton("Close");
        btnClose.setBounds((resolutionH / 2) - (220 / 2), (resolutionW / 2) - (50 / 2), 220, 50);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        
        this.add(btnClose);

        this.setVisible(true);

    }

    public void btnCloseActionPerformed(ActionEvent args) {
        this.setVisible(false);
        this.dispose();
    }
}
