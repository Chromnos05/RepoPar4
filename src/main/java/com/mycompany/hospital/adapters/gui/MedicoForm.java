package com.mycompany.hospital.adapters.gui;

import com.mycompany.hospital.application.service.MedicoService;
import com.mycompany.hospital.domain.model.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Oscar M
 */
public class MedicoForm extends javax.swing.JPanel {

    private final MedicoService medicoService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtEspecialidad, txtIdConsultorio;

    public MedicoForm(MedicoService medicoService, CardLayout cardLayout, JPanel contentPanel) {
        this.medicoService = medicoService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Título
        JLabel titulo = new JLabel("Gestión de Medicos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(44, 62, 80));
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        // Formulario
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false); // sin fondo para integrarse

        txtNombre = new JTextField();
        txtEspecialidad = new JTextField();
        txtIdConsultorio = new JTextField();

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Especialidad:"));
        formPanel.add(txtEspecialidad);
        formPanel.add(new JLabel("ID Consultorio:"));
        formPanel.add(txtIdConsultorio);

        // Botones
        JPanel buttonPanel = new JPanel(new GridLayout(0, 6, 10, 10));
        buttonPanel.setOpaque(false);

        JButton btnGuardar = crearBoton("Guardar");
        JButton btnModificar = crearBoton("Modificar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnCargar = crearBoton("Cargar");
        JButton btnVolver = crearBoton("Volver al inicio");

        btnGuardar.addActionListener(e -> guardarMedico());
        btnModificar.addActionListener(e -> modificarMedico());
        btnEliminar.addActionListener(e -> eliminarMedico());
        btnCargar.addActionListener(e -> cargarMedicos());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCargar);
        buttonPanel.add(btnVolver);

        // Tabla
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Especialidad", "ID Consultorio"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Médicos registrados"));
        scrollPane.setPreferredSize(new Dimension(750, 200));

        // Evento al seleccionar
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int fila = table.getSelectedRow();
                txtNombre.setText(tableModel.getValueAt(fila, 1).toString());
                txtEspecialidad.setText(tableModel.getValueAt(fila, 2).toString());
                txtIdConsultorio.setText(tableModel.getValueAt(fila, 3) != null ? tableModel.getValueAt(fila, 3).toString() : "");
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

    private void guardarMedico() {
        try {
            String nombre = txtNombre.getText();
            String especialidad = txtEspecialidad.getText();
            Integer idConsultorio = txtIdConsultorio.getText().isEmpty() ? null : Integer.parseInt(txtIdConsultorio.getText());

            Medico medico = new Medico(0, nombre, especialidad, idConsultorio);
            medicoService.crearMedico(medico);
            cargarMedicos();
            JOptionPane.showMessageDialog(this, "Médico guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarMedico() {
        int row = table.getSelectedRow();
        if (row != -1) {
            try {
                int id = (int) tableModel.getValueAt(row, 0);
                String nombre = txtNombre.getText();
                String especialidad = txtEspecialidad.getText();
                Integer idConsultorio = txtIdConsultorio.getText().isEmpty() ? null : Integer.parseInt(txtIdConsultorio.getText());

                Medico medico = new Medico(id, nombre, especialidad, idConsultorio);
                medicoService.actualizarMedico(medico);
                cargarMedicos();
                JOptionPane.showMessageDialog(this, "Médico actualizado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un médico para modificar.");
        }
    }

    private void eliminarMedico() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            medicoService.eliminarMedico(id);
            cargarMedicos();
            JOptionPane.showMessageDialog(this, "Médico eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un médico para eliminar.");
        }
    }

    private void cargarMedicos() {
        tableModel.setRowCount(0);
        List<Medico> lista = medicoService.listarMedicos();
        for (Medico m : lista) {
            tableModel.addRow(new Object[]{
                m.getId(),
                m.getNombre(),
                m.getEspecialidad(),
                m.getIdConsultorio() != null ? m.getIdConsultorio() : "-"
            });
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
