package com.mycompany.hospital.adapters.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Oscar M
 */
public class InicioPanel extends javax.swing.JPanel {

    public InicioPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));

        JLabel titulo = new JLabel("Bienvenido al Sistema Hospitalario", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(44, 62, 80));

        JLabel subtitulo = new JLabel("Selecciona una opción del menú para comenzar", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(100, 100, 100));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(new Color(236, 240, 241));
        centerPanel.add(titulo);
        centerPanel.add(subtitulo);

        add(centerPanel, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
