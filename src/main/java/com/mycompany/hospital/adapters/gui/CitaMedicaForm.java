package com.mycompany.hospital.adapters.gui;

import com.mycompany.hospital.application.service.CitaMedicaService;
import com.mycompany.hospital.domain.model.CitaMedica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Oscar M
 */
public class CitaMedicaForm extends javax.swing.JPanel {

    private final CitaMedicaService citaService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtIdPaciente, txtIdMedico, txtFecha, txtHora;

    public CitaMedicaForm(CitaMedicaService citaService, CardLayout cardLayout, JPanel contentPanel) {
        this.citaService = citaService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Gestión de Citas Médicas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(44, 62, 80));
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);

        txtIdPaciente = new JTextField();
        txtIdMedico = new JTextField();
        txtFecha = new JTextField(); // formato yyyy-MM-dd
        txtHora = new JTextField();  // formato HH:mm

        formPanel.add(new JLabel("ID Paciente:"));
        formPanel.add(txtIdPaciente);
        formPanel.add(new JLabel("ID Médico:"));
        formPanel.add(txtIdMedico);
        formPanel.add(new JLabel("Fecha (yyyy-MM-dd):"));
        formPanel.add(txtFecha);
        formPanel.add(new JLabel("Hora (HH:mm):"));
        formPanel.add(txtHora);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 6, 10, 10));
        buttonPanel.setOpaque(false);

        JButton btnGuardar = crearBoton("Guardar");
        JButton btnModificar = crearBoton("Modificar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnCargar = crearBoton("Cargar");
        JButton btnHoy = crearBoton("Citas de hoy");
        JButton btnDisponibilidad = crearBoton("Ver disponibilidad");
        JButton btnBuscarCita = crearBoton("Buscar cita por ID");
        JButton btnVolver = crearBoton("Volver al inicio");

        btnGuardar.addActionListener(e -> guardarCita());
        btnModificar.addActionListener(e -> modificarCita());
        btnEliminar.addActionListener(e -> eliminarCita());
        btnCargar.addActionListener(e -> cargarCitas());
        btnHoy.addActionListener(e -> cargarCitasDeHoy());
        btnDisponibilidad.addActionListener(e -> mostrarDisponibilidad());
        btnBuscarCita.addActionListener(e -> buscarCitaInfoPorId());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCargar);
        buttonPanel.add(btnHoy);
        buttonPanel.add(btnDisponibilidad);
        buttonPanel.add(btnBuscarCita);
        buttonPanel.add(btnVolver);

        tableModel = new DefaultTableModel(new String[]{"ID", "ID Paciente", "ID Médico", "Fecha", "Hora"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Citas médicas registradas"));
        scrollPane.setPreferredSize(new Dimension(750, 200));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtIdPaciente.setText(tableModel.getValueAt(row, 1).toString());
                txtIdMedico.setText(tableModel.getValueAt(row, 2).toString());
                txtFecha.setText(tableModel.getValueAt(row, 3).toString());
                txtHora.setText(tableModel.getValueAt(row, 4).toString());
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

    private void guardarCita() {
        try {
            int idPaciente = Integer.parseInt(txtIdPaciente.getText());
            int idMedico = Integer.parseInt(txtIdMedico.getText());
            LocalDate fecha = LocalDate.parse(txtFecha.getText());
            String hora = txtHora.getText();

            CitaMedica cita = new CitaMedica(0, idPaciente, idMedico, fecha, hora);
            citaService.crearCita(cita);
            cargarCitas();
            JOptionPane.showMessageDialog(this, "Cita médica guardada correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarCita() {
        int row = table.getSelectedRow();
        if (row != -1) {
            try {
                int id = (int) tableModel.getValueAt(row, 0);
                int idPaciente = Integer.parseInt(txtIdPaciente.getText());
                int idMedico = Integer.parseInt(txtIdMedico.getText());
                LocalDate fecha = LocalDate.parse(txtFecha.getText());
                String hora = txtHora.getText();

                CitaMedica cita = new CitaMedica(id, idPaciente, idMedico, fecha, hora);
                citaService.actualizarCita(cita);
                cargarCitas();
                JOptionPane.showMessageDialog(this, "Cita modificada correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para modificar.");
        }
    }

    private void eliminarCita() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            citaService.eliminarCita(id);
            cargarCitas();
            JOptionPane.showMessageDialog(this, "Cita eliminada.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para eliminar.");
        }
    }

    private void cargarCitas() {
        tableModel.setRowCount(0);
        List<CitaMedica> lista = citaService.listarCitas();
        for (CitaMedica c : lista) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getIdPaciente(),
                c.getIdMedico(),
                c.getFecha(),
                c.getHora()
            });
        }
    }
    
    private void cargarCitasDeHoy() {
        tableModel.setRowCount(0);
        List<CitaMedica> lista = citaService.obtenerCitasDeHoy();
        for (CitaMedica c : lista) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getIdPaciente(),
                c.getIdMedico(),
                c.getFecha(),
                c.getHora()
            });
        }
    }
    
    private void mostrarDisponibilidad() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese ID del médico:");
            String fechaStr = JOptionPane.showInputDialog(this, "Ingrese fecha (yyyy-MM-dd):");

            if (idStr != null && fechaStr != null) {
                int idMedico = Integer.parseInt(idStr);
                LocalDate fecha = LocalDate.parse(fechaStr);

                List<String> disponibles = citaService.obtenerHorasDisponibles(idMedico, fecha);

                if (disponibles.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El médico no tiene disponibilidad ese día.");
                } else {
                    StringBuilder mensaje = new StringBuilder("Horas disponibles:\n");
                    disponibles.forEach(h -> mensaje.append("• ").append(h).append("\n"));

                    JTextArea area = new JTextArea(mensaje.toString());
                    area.setEditable(false);
                    area.setBackground(new Color(245, 245, 245));
                    area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    area.setMargin(new Insets(8, 8, 8, 8));

                    JOptionPane.showMessageDialog(this, new JScrollPane(area), "Disponibilidad", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida o error interno.");
        }
    }
    
    private void buscarCitaInfoPorId() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el ID de la cita médica:");
        if (input != null && !input.isEmpty()) {
            try {
                int idCita = Integer.parseInt(input);
                Optional<CitaMedica> resultado = citaService.obtenerInfoPorId(idCita);

                if (resultado.isPresent()) {
                    CitaMedica c = resultado.get();
                    StringBuilder mensaje = new StringBuilder();
                    mensaje.append("ID Cita: ").append(c.getId()).append("\n");
                    mensaje.append("ID Paciente: ").append(c.getIdPaciente()).append("\n");
                    mensaje.append("ID Médico: ").append(c.getIdMedico()).append("\n");
                    mensaje.append("Fecha: ").append(c.getFecha()).append("\n");
                    mensaje.append("Hora: ").append(c.getHora());

                    JOptionPane.showMessageDialog(this, mensaje.toString(), "Cita encontrada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ninguna cita con ese ID.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
