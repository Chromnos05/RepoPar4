package com.mycompany.hospital.adapters.gui;

import com.mycompany.hospital.application.service.PacienteService;
import com.mycompany.hospital.domain.model.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public class PacienteForm extends javax.swing.JPanel {

    private final PacienteService pacienteService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtDireccion, txtFechaNacimiento, txtIdMedico;

    public PacienteForm(PacienteService pacienteService, CardLayout cardLayout, JPanel contentPanel) {
        this.pacienteService = pacienteService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // espacio interno amplio

        //Título
        JLabel titulo = new JLabel("Gestión de Pacientes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(44, 62, 80));
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        //Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false); // sin fondo para integrarse

        txtNombre = new JTextField();
        txtDireccion = new JTextField();
        txtFechaNacimiento = new JTextField(); // formato: yyyy-MM-dd
        txtIdMedico = new JTextField();

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Dirección:"));
        formPanel.add(txtDireccion);
        formPanel.add(new JLabel("Fecha Nacimiento (yyyy-MM-dd):"));
        formPanel.add(txtFechaNacimiento);
        formPanel.add(new JLabel("ID Médico Asignado:"));
        formPanel.add(txtIdMedico);

        //Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setOpaque(false);

        JButton btnGuardar = crearBoton("Guardar");
        JButton btnModificar = crearBoton("Modificar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnCargar = crearBoton("Cargar");
        JButton btnBuscarPorMedico = crearBoton("Buscar por médico");
        JButton btnVolver = crearBoton("Volver al inicio");

        btnGuardar.addActionListener(e -> guardarPaciente());
        btnModificar.addActionListener(e -> modificarPaciente());
        btnEliminar.addActionListener(e -> eliminarPaciente());
        btnCargar.addActionListener(e -> cargarPacientes());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));
        btnBuscarPorMedico.addActionListener(e -> buscarPacientesPorMedico());

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCargar);
        buttonPanel.add(btnBuscarPorMedico);
        buttonPanel.add(btnVolver);

        //Tabla
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Fecha Nac.", "Dirección", "Médico"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pacientes registrados"));
        scrollPane.setPreferredSize(new Dimension(750, 200));

        //Evento de selección de tabla
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int fila = table.getSelectedRow();
                txtNombre.setText(tableModel.getValueAt(fila, 1).toString());
                txtFechaNacimiento.setText(tableModel.getValueAt(fila, 2).toString());
                txtDireccion.setText(tableModel.getValueAt(fila, 3).toString());
                Object medico = tableModel.getValueAt(fila, 4);
                txtIdMedico.setText(medico != null && !medico.equals("-") ? medico.toString() : "");
            }
        });

        //Ensamblar todo
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
    
    private void guardarPaciente() {
        try {
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            LocalDate fechaNacimiento = LocalDate.parse(txtFechaNacimiento.getText());
            Integer idMedico = txtIdMedico.getText().isEmpty() ? null : Integer.parseInt(txtIdMedico.getText());

            Paciente paciente = new Paciente(0, nombre, fechaNacimiento, direccion, idMedico);
            pacienteService.crearPaciente(paciente);
            cargarPacientes();

            JOptionPane.showMessageDialog(this, "Paciente guardado correctamente");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPaciente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            pacienteService.eliminarPaciente(id);
            cargarPacientes();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente para eliminar");
        }
    }
    
    private void modificarPaciente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {

            // ✅ Validación previa
            if (txtNombre.getText().isEmpty() || txtFechaNacimiento.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y Fecha de Nacimiento son obligatorios");
                return;
            }

            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String nombre = txtNombre.getText();
                String direccion = txtDireccion.getText();
                LocalDate fechaNacimiento = LocalDate.parse(txtFechaNacimiento.getText());
                Integer idMedico = txtIdMedico.getText().isEmpty() ? null : Integer.parseInt(txtIdMedico.getText());

                Paciente paciente = new Paciente(id, nombre, fechaNacimiento, direccion, idMedico);
                pacienteService.actualizarPaciente(paciente);
                cargarPacientes();
                JOptionPane.showMessageDialog(this, "Paciente modificado correctamente");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente para modificar");
        }
    }

    private void cargarPacientes() {
        tableModel.setRowCount(0);
        List<Paciente> pacientes = pacienteService.listarPacientes();
        for (Paciente p : pacientes) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getFechaNacimiento(),
                p.getDireccion(),
                p.getIdMedicoAsignado() != null ? p.getIdMedicoAsignado() : "-"
            });
        }
    }
    
    private void buscarPacientesPorMedico() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el ID del médico:");
        if (input != null && !input.isEmpty()) {
            try {
                int idMedico = Integer.parseInt(input);
                List<Paciente> lista = pacienteService.obtenerPorIdMedico(idMedico);
                tableModel.setRowCount(0);
                for (Paciente p : lista) {
                    tableModel.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getFechaNacimiento(),
                        p.getDireccion(),
                        p.getIdMedicoAsignado() != null ? p.getIdMedicoAsignado() : "-"
                    });
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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

