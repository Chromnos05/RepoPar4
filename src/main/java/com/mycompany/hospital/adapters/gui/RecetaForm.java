/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.hospital.adapters.gui;
import com.mycompany.hospital.application.service.RecetaService;
import com.mycompany.hospital.domain.model.Receta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
/**
 *
 * @author Oscar M
 */
public class RecetaForm extends javax.swing.JPanel {

    /**
     * Creates new form RecetaForm
     */
   private final RecetaService recetaService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtIdDiagnostico, txtFecha;
    private Receta recetaSeleccionada;

    public RecetaForm(RecetaService recetaService, CardLayout cardLayout, JPanel contentPanel) {
        this.recetaService = recetaService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Gestión de Recetas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        txtIdDiagnostico = new JTextField();
        txtFecha = new JTextField();
        formPanel.add(new JLabel("ID Diagnóstico:"));
        formPanel.add(txtIdDiagnostico);
        formPanel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        formPanel.add(txtFecha);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton btnGuardar = crearBoton("Guardar");
        JButton btnModificar = crearBoton("Modificar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnCargar = crearBoton("Cargar Todas");
        JButton btnConsultas = crearBoton("Consultas...");
        JButton btnVolver = crearBoton("Volver al inicio");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCargar);
        buttonPanel.add(btnConsultas);
        buttonPanel.add(btnVolver);

        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setOpaque(false);
        topPanel.add(titulo, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "ID Diagnóstico", "Fecha"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarReceta());
        btnModificar.addActionListener(e -> modificarReceta());
        btnEliminar.addActionListener(e -> eliminarReceta());
        btnCargar.addActionListener(e -> cargarRecetas());
        btnConsultas.addActionListener(e -> abrirDialogoConsultas());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int fila = table.getSelectedRow();
                int idReceta = (int)tableModel.getValueAt(fila, 0);
                recetaSeleccionada = recetaService.obtenerRecetaPorId(idReceta);
                if (recetaSeleccionada != null) {
                    txtIdDiagnostico.setText(String.valueOf(recetaSeleccionada.getIdDiagnostico()));
                    txtFecha.setText(recetaSeleccionada.getFecha().toString());
                }
            } else {
                if (table.getSelectedRow() == -1) {
                    recetaSeleccionada = null;
                }
            }
        });
    }

    private void abrirDialogoConsultas() {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        DialogoConsultasReceta dialogo = new DialogoConsultasReceta(owner, recetaService, recetaSeleccionada);
        dialogo.setVisible(true);
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

    private void guardarReceta() {
        try {
            int idDiagnostico = Integer.parseInt(txtIdDiagnostico.getText());
            LocalDate fecha = LocalDate.parse(txtFecha.getText());

            Receta receta = new Receta(0, idDiagnostico, fecha);
            recetaService.crearReceta(receta);
            cargarRecetas();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Receta guardada.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de Diagnóstico debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarReceta() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                int idDiagnostico = Integer.parseInt(txtIdDiagnostico.getText());
                LocalDate fecha = LocalDate.parse(txtFecha.getText());

                Receta receta = new Receta(id, idDiagnostico, fecha);
                recetaService.actualizarReceta(receta);
                cargarRecetas();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Receta modificada.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de Diagnóstico debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una receta para modificar.");
        }
    }

    private void eliminarReceta() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                recetaService.eliminarReceta(id);
                cargarRecetas();
                limpiarCampos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una receta para eliminar.");
        }
    }

    private void cargarRecetas() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<Receta> recetas = recetaService.listarRecetas();
        for (Receta r : recetas) {
            model.addRow(new Object[]{r.getId(), r.getIdDiagnostico(), r.getFecha().toString()});
        }
    }
    
    private void limpiarCampos() {
        txtIdDiagnostico.setText("");
        txtFecha.setText("");
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
