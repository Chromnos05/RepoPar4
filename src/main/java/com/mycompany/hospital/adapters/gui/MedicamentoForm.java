/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.hospital.adapters.gui;
import com.mycompany.hospital.application.service.MedicamentoService;
import com.mycompany.hospital.domain.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Oscar M
 */
public class MedicamentoForm extends javax.swing.JPanel {

    /**
     * Creates new form MedicamentoForm
     */
     private final MedicamentoService medicamentoService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtIdLaboratorio, txtUnidades;
    private JCheckBox chkNecesitaReceta;
    private Medicamento medicamentoSeleccionado; // Guardar el objeto completo

    public MedicamentoForm(MedicamentoService medicamentoService, CardLayout cardLayout, JPanel contentPanel) {
        this.medicamentoService = medicamentoService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        initUI();
    }

     private void initUI() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Gestión de Medicamentos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(44, 62, 80));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setOpaque(false);

        txtNombre = new JTextField();
        txtIdLaboratorio = new JTextField();
        txtUnidades = new JTextField();
        chkNecesitaReceta = new JCheckBox("Necesita Receta");
        chkNecesitaReceta.setOpaque(false);

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("ID Laboratorio:"));
        formPanel.add(txtIdLaboratorio);
        formPanel.add(new JLabel("Unidades Disponibles:"));
        formPanel.add(txtUnidades);
        formPanel.add(new JLabel("")); // Espacio en blanco
        formPanel.add(chkNecesitaReceta);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setOpaque(false);
        JButton btnGuardar = crearBoton("Guardar");
        JButton btnModificar = crearBoton("Modificar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnCargar = crearBoton("Cargar");
        
        // NUEVO Y ÚNICO BOTÓN DE CONSULTAS
        JButton btnConsultas = crearBoton("Consultas...");
        
        JButton btnVolver = crearBoton("Volver al inicio");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCargar);
        buttonPanel.add(btnConsultas); // Añadir el nuevo botón
        buttonPanel.add(btnVolver);
        
        

        JPanel topPanel = new JPanel(new BorderLayout(0,10));
        topPanel.setOpaque(false);
        topPanel.add(titulo, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Unidades", "Necesita Receta"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- Action Listeners ---
        btnGuardar.addActionListener(e -> guardarMedicamento());
        btnModificar.addActionListener(e -> modificarMedicamento());
        btnEliminar.addActionListener(e -> eliminarMedicamento());
        btnCargar.addActionListener(e -> cargarMedicamentos());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        btnConsultas.addActionListener(e -> abrirDialogoConsultas());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int fila = table.getSelectedRow();
                int medId = (int) tableModel.getValueAt(fila, 0);
                medicamentoSeleccionado = medicamentoService.obtenerMedicamentoPorId(medId);
                if (medicamentoSeleccionado != null) {
                    txtNombre.setText(medicamentoSeleccionado.getNombre());
                    txtUnidades.setText(String.valueOf(medicamentoSeleccionado.getUnidadesDisponibles()));
                    txtIdLaboratorio.setText(medicamentoSeleccionado.getIdLaboratorio() != null ? String.valueOf(medicamentoSeleccionado.getIdLaboratorio()) : "");
                    chkNecesitaReceta.setSelected(medicamentoSeleccionado.isNecesitaReceta());
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

    private void guardarMedicamento() {
        try {
            String nombre = txtNombre.getText();
            int unidades = Integer.parseInt(txtUnidades.getText());
            Integer idLab = txtIdLaboratorio.getText().isEmpty() ? null : Integer.parseInt(txtIdLaboratorio.getText());
            boolean necesitaReceta = chkNecesitaReceta.isSelected();

            Medicamento med = new Medicamento(0, nombre, idLab, unidades, necesitaReceta);
            medicamentoService.crearMedicamento(med);
            cargarMedicamentos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Medicamento guardado.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce números válidos para Unidades e ID Laboratorio.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarMedicamento() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String nombre = txtNombre.getText();
                int unidades = Integer.parseInt(txtUnidades.getText());
                Integer idLab = txtIdLaboratorio.getText().isEmpty() ? null : Integer.parseInt(txtIdLaboratorio.getText());
                boolean necesitaReceta = chkNecesitaReceta.isSelected();

                Medicamento med = new Medicamento(id, nombre, idLab, unidades, necesitaReceta);
                medicamentoService.actualizarMedicamento(med);
                cargarMedicamentos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Medicamento modificado.");
            } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this, "Por favor, introduce números válidos para Unidades e ID Laboratorio.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un medicamento para modificar.");
        }
    }

    private void eliminarMedicamento() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                medicamentoService.eliminarMedicamento(id);
                cargarMedicamentos();
                limpiarCampos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un medicamento para eliminar.");
        }
    }

    private void cargarMedicamentos() {
        tableModel.setRowCount(0);
        List<Medicamento> medicamentos = medicamentoService.listarMedicamentos();
        for (Medicamento m : medicamentos) {
            tableModel.addRow(new Object[]{
                m.getId(),
                m.getNombre(),
                m.getUnidadesDisponibles(),
                m.isNecesitaReceta() ? "Sí" : "No"
            });
        }
    }
    
     private void abrirDialogoConsultas() {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        DialogoConsultasMedicamento dialogo = new DialogoConsultasMedicamento(owner, medicamentoService, medicamentoSeleccionado);
        dialogo.setVisible(true);
    }

    // Método genérico para mostrar listas en un JOptionPane
    private void mostrarListaEnDialog(List<String> items, String titulo) {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.", titulo, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String mensaje = String.join("\n", items);
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        JOptionPane.showMessageDialog(this, scrollPane, titulo + " de: " + medicamentoSeleccionado.getNombre(), JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtIdLaboratorio.setText("");
        txtUnidades.setText("");
        chkNecesitaReceta.setSelected(false);
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
