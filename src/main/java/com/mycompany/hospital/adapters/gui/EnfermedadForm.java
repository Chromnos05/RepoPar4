/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.hospital.adapters.gui;
import com.mycompany.hospital.application.service.EnfermedadService;
import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.model.Enfermedad;
import com.mycompany.hospital.domain.model.Sintoma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Oscar M
 */
public class EnfermedadForm extends javax.swing.JPanel {

    /**
     * Creates new form EnfermedadForm
     */
     private final EnfermedadService enfermedadService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private Enfermedad enfermedadSeleccionada;

    public EnfermedadForm(EnfermedadService enfermedadService, CardLayout cardLayout, JPanel contentPanel) {
        this.enfermedadService = enfermedadService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Gestión de Enfermedades");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(44, 62, 80));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        txtNombre = new JTextField();
        txtNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtNombre.getPreferredSize().height));

        txtDescripcion = new JTextArea(5, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(new JLabel("Descripción:"));
        formPanel.add(scrollDescripcion);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setOpaque(false);
        JButton btnGuardar = crearBoton("Guardar");
        JButton btnModificar = crearBoton("Modificar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnCargar = crearBoton("Cargar");
           JButton btnConsultas = crearBoton("Consultas...");
        JButton btnVolver = crearBoton("Volver al inicio");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCargar);
         buttonPanel.add(btnConsultas);// Añadir al panel
        buttonPanel.add(btnVolver); 

        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setOpaque(false);
        topPanel.add(titulo, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- Action Listeners ---
        btnGuardar.addActionListener(e -> guardarEnfermedad());
        btnModificar.addActionListener(e -> modificarEnfermedad());
        btnEliminar.addActionListener(e -> eliminarEnfermedad());
        btnCargar.addActionListener(e -> cargarEnfermedades());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));
         btnConsultas.addActionListener(e -> abrirDialogoConsultas());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int fila = table.getSelectedRow();
                int id = (int) tableModel.getValueAt(fila, 0);
                // Guardamos el objeto completo al seleccionar
                enfermedadSeleccionada = enfermedadService.obtenerEnfermedadPorId(id);
                if (enfermedadSeleccionada != null) {
                    txtNombre.setText(enfermedadSeleccionada.getNombre());
                    txtDescripcion.setText(enfermedadSeleccionada.getDescripcion());
                }
            }
        });
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
    
    private void guardarEnfermedad() {
        try {
            Enfermedad enfermedad = new Enfermedad(0, txtNombre.getText(), txtDescripcion.getText());
            enfermedadService.crearEnfermedad(enfermedad);
            cargarEnfermedades();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Enfermedad guardada.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEnfermedad() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Enfermedad enfermedad = new Enfermedad(id, txtNombre.getText(), txtDescripcion.getText());
                enfermedadService.actualizarEnfermedad(enfermedad);
                cargarEnfermedades();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Enfermedad modificada.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una enfermedad para modificar.");
        }
    }

    private void eliminarEnfermedad() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                enfermedadService.eliminarEnfermedad(id);
                cargarEnfermedades();
                limpiarCampos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una enfermedad para eliminar.");
        }
    }

    private void cargarEnfermedades() {
        tableModel.setRowCount(0);
        List<Enfermedad> enfermedades = enfermedadService.listarEnfermedades();
        for (Enfermedad e : enfermedades) {
            tableModel.addRow(new Object[]{e.getId(), e.getNombre()});
        }
    }
    // --- MÉTODOS PARA MOSTRAR RESULTADOS DE CONSULTAS ---
     private void abrirDialogoConsultas() {
        if (enfermedadSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una enfermedad de la tabla primero.", "Acción Requerida", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        DialogoConsultasEnfermedad dialogo = new DialogoConsultasEnfermedad(owner, enfermedadService, enfermedadSeleccionada);
        dialogo.setVisible(true);
    }
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        table.clearSelection();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
