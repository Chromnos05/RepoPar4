package com.mycompany.hospital.adapters.gui;

import com.mycompany.hospital.application.service.PacienteService;
import com.mycompany.hospital.application.usecase.PacienteServiceImpl;
import com.mycompany.hospital.domain.repository.PacienteRepository;
import com.mycompany.hospital.infrastructure.postgresql.PacienteRepositoryPostgres;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Oscar M
 */
public class Main extends javax.swing.JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    private final PacienteForm pacienteForm;

    public Main() {
        // Crear servicios y formularios
        PacienteRepository pacienteRepo = new PacienteRepositoryPostgres();
        PacienteService pacienteService = new PacienteServiceImpl(pacienteRepo);
        pacienteForm = new PacienteForm(pacienteService, cardLayout, contentPanel);

        initUI();
    }

    private void initUI() {
        setTitle("Sistema Hospitalario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔹 Panel lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(44, 62, 80));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));

        // 🔹 Botones del menú
        JButton btnPacientes = crearBotonMenu("Pacientes");
        JButton btnMedicos = crearBotonMenu("Médicos");
        JButton btnEmpleados = crearBotonMenu("Empleados");
        JButton btnSalir = crearBotonMenu("Salir");

        btnPacientes.addActionListener(e -> mostrarPanel("pacientes"));
        btnMedicos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo de médicos no disponible aún"));
        btnEmpleados.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo de empleados no disponible aún"));
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        // 🔹 Agregar botones al panel
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(btnPacientes);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnMedicos);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnEmpleados);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(btnSalir);
        menuPanel.add(Box.createVerticalStrut(20));

        // 🔹 Panel central
        contentPanel.add(new InicioPanel(), "inicio");
        contentPanel.add(pacienteForm, "pacientes");

        // 🔹 Agregar todo a la ventana
        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "inicio");
        
        setVisible(true);
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void mostrarPanel(String nombre) {
        cardLayout.show(contentPanel, nombre);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        SwingUtilities.invokeLater(Main::new);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
