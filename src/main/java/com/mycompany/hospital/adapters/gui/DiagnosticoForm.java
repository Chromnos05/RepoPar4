package com.mycompany.hospital.adapters.gui;

import com.mycompany.hospital.application.service.DiagnosticoService;
import com.mycompany.hospital.domain.model.Diagnostico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public class DiagnosticoForm extends javax.swing.JPanel {

    private final DiagnosticoService diagnosticoService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtIdCita, txtIdEnfermedad, txtObservaciones;

    public DiagnosticoForm(DiagnosticoService diagnosticoService, CardLayout cardLayout, JPanel contentPanel) {
        this.diagnosticoService = diagnosticoService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Gestión de Diagnósticos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(44, 62, 80));
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);

        txtIdCita = new JTextField();
        txtIdEnfermedad = new JTextField();
        txtObservaciones = new JTextField();

        formPanel.add(new JLabel("ID Cita:"));
        formPanel.add(txtIdCita);
        formPanel.add(new JLabel("ID Enfermedad:"));
        formPanel.add(txtIdEnfermedad);
        formPanel.add(new JLabel("Observaciones:"));
        formPanel.add(txtObservaciones);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 6, 10, 10)); // 3 columnas, filas dinámicas
        buttonPanel.setOpaque(false);

        JButton btnGuardar = crearBoton("Guardar");
        JButton btnModificar = crearBoton("Modificar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnCargar = crearBoton("Cargar");
        JButton btnHistorialPaciente = crearBoton("Historial por paciente");
        JButton btnDiagnosticosPorFecha = crearBoton("Diagnósticos por fecha");
        JButton btnVolver = crearBoton("Volver al inicio");

        btnGuardar.addActionListener(e -> guardarDiagnostico());
        btnModificar.addActionListener(e -> modificarDiagnostico());
        btnEliminar.addActionListener(e -> eliminarDiagnostico());
        btnCargar.addActionListener(e -> cargarDiagnosticos());
        btnHistorialPaciente.addActionListener(e -> buscarPorPaciente());
        btnDiagnosticosPorFecha.addActionListener(e -> buscarDiagnosticosPorFecha());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCargar);
        buttonPanel.add(btnHistorialPaciente);
        buttonPanel.add(btnDiagnosticosPorFecha);
        buttonPanel.add(btnVolver);

        tableModel = new DefaultTableModel(new String[]{"ID", "ID Cita", "ID Enfermedad", "Observaciones"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Diagnósticos registrados"));
        scrollPane.setPreferredSize(new Dimension(750, 200));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtIdCita.setText(tableModel.getValueAt(row, 1).toString());
                txtIdEnfermedad.setText(tableModel.getValueAt(row, 2).toString());
                txtObservaciones.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.add(titulo);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(formPanel);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(buttonPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    private void guardarDiagnostico() {
        try {
            int idCita = Integer.parseInt(txtIdCita.getText());
            int idEnfermedad = Integer.parseInt(txtIdEnfermedad.getText());
            String observaciones = txtObservaciones.getText();

            Diagnostico d = new Diagnostico(0, idCita, idEnfermedad, observaciones);
            diagnosticoService.crearDiagnostico(d);
            cargarDiagnosticos();
            JOptionPane.showMessageDialog(this, "Diagnóstico guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarDiagnostico() {
        int row = table.getSelectedRow();
        if (row != -1) {
            try {
                int id = (int) tableModel.getValueAt(row, 0);
                int idCita = Integer.parseInt(txtIdCita.getText());
                int idEnfermedad = Integer.parseInt(txtIdEnfermedad.getText());
                String observaciones = txtObservaciones.getText();

                Diagnostico d = new Diagnostico(id, idCita, idEnfermedad, observaciones);
                diagnosticoService.actualizarDiagnostico(d);
                cargarDiagnosticos();
                JOptionPane.showMessageDialog(this, "Diagnóstico modificado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un diagnóstico para modificar.");
        }
    }

    private void eliminarDiagnostico() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            diagnosticoService.eliminarDiagnostico(id);
            cargarDiagnosticos();
            JOptionPane.showMessageDialog(this, "Diagnóstico eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un diagnóstico para eliminar.");
        }
    }

    private void cargarDiagnosticos() {
        tableModel.setRowCount(0);
        List<Diagnostico> lista = diagnosticoService.listarDiagnosticos();
        for (Diagnostico d : lista) {
            tableModel.addRow(new Object[]{
                d.getId(),
                d.getIdCita(),
                d.getIdEnfermedad(),
                d.getObservaciones()
            });
        }
    }
    
    private void buscarPorPaciente() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el ID del paciente:");
        if (input != null && !input.isEmpty()) {
            try {
                int idPaciente = Integer.parseInt(input);
                List<Diagnostico> lista = diagnosticoService.obtenerPorIdPaciente(idPaciente);
                tableModel.setRowCount(0);
                for (Diagnostico d : lista) {
                    tableModel.addRow(new Object[]{
                        d.getId(),
                        d.getIdCita(),
                        d.getIdEnfermedad(),
                        d.getObservaciones()
                    });
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void buscarDiagnosticosPorFecha() {
        try {
            String desdeStr = JOptionPane.showInputDialog(this, "Desde (yyyy-MM-dd):");
            String hastaStr = JOptionPane.showInputDialog(this, "Hasta (yyyy-MM-dd):");

            if (desdeStr != null && hastaStr != null) {
                LocalDate desde = LocalDate.parse(desdeStr);
                LocalDate hasta = LocalDate.parse(hastaStr);

                List<Diagnostico> lista = diagnosticoService.obtenerPorRangoDeFechas(desde, hasta);
                tableModel.setRowCount(0);
                for (Diagnostico d : lista) {
                    tableModel.addRow(new Object[]{
                        d.getId(),
                        d.getIdCita(),
                        d.getIdEnfermedad(),
                        d.getObservaciones()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fechas inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
