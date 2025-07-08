/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.hospital.adapters.gui;

import javax.swing.JDialog;
import com.mycompany.hospital.application.service.EnfermedadService;
import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.model.Enfermedad;
import com.mycompany.hospital.domain.model.Sintoma;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Danie
 */
public class DialogoConsultasEnfermedad extends JDialog {

    /**
     * Creates new form DialogoConsultasEnfermedad
     */
    private final EnfermedadService enfermedadService;
    private final Enfermedad enfermedadSeleccionada;
    private JTextArea areaResultados;
     private JTextField txtFechaInicio, txtFechaFin;

    public DialogoConsultasEnfermedad(Frame owner, EnfermedadService service, Enfermedad enfermedad) {
        super(owner, "Panel de Consultas de Enfermedad", true);
        this.enfermedadService = service;
        this.enfermedadSeleccionada = enfermedad;
        initUI();
    }

    private void initUI() {
        setSize(700, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        // --- Panel de Acciones ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String titulo = (enfermedadSeleccionada != null)
                ? "Consultas para: " + enfermedadSeleccionada.getNombre()
                : "Seleccione una enfermedad en la ventana principal";
        panelAcciones.setBorder(BorderFactory.createTitledBorder(titulo));
        
        
        
         JPanel panelBusquedaFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusquedaFecha.setBorder(BorderFactory.createTitledBorder("Búsqueda General"));
        txtFechaInicio = new JTextField(10);
        txtFechaFin = new JTextField(10);
        JButton btnBuscarPorFecha = new JButton("Buscar Enfermedades por Fecha de Diagnóstico");
        
        panelBusquedaFecha.add(new JLabel("Desde (YYYY-MM-DD):"));
        panelBusquedaFecha.add(txtFechaInicio);
        panelBusquedaFecha.add(new JLabel("Hasta:"));
        panelBusquedaFecha.add(txtFechaFin);
        panelBusquedaFecha.add(btnBuscarPorFecha);
    
        
        JButton btnVerSintomas = new JButton("Ver Síntomas");
        JButton btnVerDiagnosticos = new JButton("Ver Diagnósticos Asociados");
        
        panelAcciones.add(btnVerSintomas);
        panelAcciones.add(btnVerDiagnosticos);

        // Deshabilitar botones si no se seleccionó ninguna enfermedad
        if (enfermedadSeleccionada == null) {
            for (Component c : panelAcciones.getComponents()) {
                if (c instanceof JButton) c.setEnabled(false);
            }
        }

        // --- Área de Resultados ---
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));

      
        
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.add(panelAcciones);
        panelSuperior.add(panelBusquedaFecha); // Añadimos el nuevo panel

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelSuperior, scrollResultados);
splitPane.setResizeWeight(0.3); // 30% panel superior, 70% resultados
splitPane.setOneTouchExpandable(true);
add(splitPane, BorderLayout.CENTER);

        // --- Action Listeners ---
        btnVerSintomas.addActionListener(e -> mostrarSintomas());
        btnVerDiagnosticos.addActionListener(e -> mostrarDiagnosticos());
        btnBuscarPorFecha.addActionListener(e -> buscarPorFecha());
    }

    private void mostrarSintomas() {
        List<Sintoma> sintomas = enfermedadService.listarSintomasDeEnfermedad(enfermedadSeleccionada.getId());
        String resultado = sintomas.stream()
                .map(s -> "- " + s.getDescripcion())
                .collect(Collectors.joining("\n"));
        areaResultados.setText("--- Síntomas ---\n" + (resultado.isEmpty() ? "No hay síntomas registrados." : resultado));
    }

    private void mostrarDiagnosticos() {
        List<Diagnostico> diagnosticos = enfermedadService.listarDiagnosticosDeEnfermedad(enfermedadSeleccionada.getId());
        String resultado = diagnosticos.stream()
                .map(d -> String.format("- ID Diag: %d, ID Cita: %d\n  Obs: %s", d.getId(), d.getIdCita(), d.getObservaciones()))
                .collect(Collectors.joining("\n\n"));
        areaResultados.setText("--- Diagnósticos Asociados ---\n" + (resultado.isEmpty() ? "No hay diagnósticos registrados." : resultado));
    }
    
    private void buscarPorFecha() {
        try {
            LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText());
            LocalDate fechaFin = LocalDate.parse(txtFechaFin.getText());
            
            List<Enfermedad> enfermedades = enfermedadService.listarDiagnosticadasEnPeriodo(fechaInicio, fechaFin);
            
            String resultado = enfermedades.stream()
                .map(e -> "- ID: " + e.getId() + ", Nombre: " + e.getNombre())
                .collect(Collectors.joining("\n"));
            
            areaResultados.setText(String.format("--- Enfermedades Diagnosticadas entre %s y %s ---\n%s", 
                fechaInicio, fechaFin, (resultado.isEmpty() ? "Ninguna." : resultado)));

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasEnfermedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasEnfermedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasEnfermedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasEnfermedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
