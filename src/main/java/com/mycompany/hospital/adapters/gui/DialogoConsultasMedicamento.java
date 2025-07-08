/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.hospital.adapters.gui;

import com.mycompany.hospital.application.service.*;
import com.mycompany.hospital.domain.model.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Danie
 */
public class DialogoConsultasMedicamento extends JDialog {

    /**
     * Creates new form DialogoConsultaMedicamento
     */
    private final MedicamentoService medicamentoService;
    private final Medicamento medicamentoSeleccionado;

    private JTextArea areaResultados;
    private JTextField txtBusquedaPaciente, txtBusquedaAuxiliar;

    public DialogoConsultasMedicamento(Frame owner, MedicamentoService service, Medicamento medicamento) {
        super(owner, "Panel de Consultas de Medicamento", true);
        this.medicamentoService = service;
        this.medicamentoSeleccionado = medicamento;
        initUI();
    }

    private void initUI() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        // --- Panel de Consultas sobre el Medicamento Seleccionado ---
        JPanel panelSeleccionado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSeleccionado.setBorder(BorderFactory.createTitledBorder("Consultas sobre el Medicamento Seleccionado"));
        
        JButton btnContra = new JButton("Contraindicaciones");
        JButton btnPrincipios = new JButton("Principios Activos");
        JButton btnLab = new JButton("Laboratorio");
        JButton btnRecetas = new JButton("Ver en Recetas");
        
        panelSeleccionado.add(btnContra);
        panelSeleccionado.add(btnPrincipios);
        panelSeleccionado.add(btnLab);
        panelSeleccionado.add(btnRecetas);

        if (medicamentoSeleccionado == null) {
            panelSeleccionado.setEnabled(false);
            for(Component c : panelSeleccionado.getComponents()) c.setEnabled(false);
        } else {
             btnLab.setEnabled(medicamentoSeleccionado.getIdLaboratorio() != null);
        }
        
        // --- Panel de Búsquedas Generales ---
        JPanel panelBusquedas = new JPanel();
        panelBusquedas.setLayout(new BoxLayout(panelBusquedas, BoxLayout.Y_AXIS));
        panelBusquedas.setBorder(BorderFactory.createTitledBorder("Búsquedas Generales"));

        JPanel panelPaciente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBusquedaPaciente = new JTextField(10);
        JButton btnBuscarPorPaciente = new JButton("Buscar Medicamentos por ID Paciente");
        panelPaciente.add(new JLabel("ID Paciente:"));
        panelPaciente.add(txtBusquedaPaciente);
        panelPaciente.add(btnBuscarPorPaciente);

        JPanel panelAuxiliar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBusquedaAuxiliar = new JTextField(15);
        JButton btnBuscarLab = new JButton("Buscar Laboratorio por Nombre");
        JButton btnBuscarPrincipio = new JButton("Buscar Principio Activo por Nombre");
        panelAuxiliar.add(new JLabel("Nombre:"));
        panelAuxiliar.add(txtBusquedaAuxiliar);
        panelAuxiliar.add(btnBuscarLab);
        panelAuxiliar.add(btnBuscarPrincipio);
        
        panelBusquedas.add(panelPaciente);
        panelBusquedas.add(panelAuxiliar);

        // --- Área de Resultados ---
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // --- Ensamblaje ---
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.add(panelSeleccionado);
        panelSuperior.add(panelBusquedas);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollResultados, BorderLayout.CENTER);

        // --- Action Listeners ---
        btnContra.addActionListener(e -> mostrarContraindicaciones());
        btnPrincipios.addActionListener(e -> mostrarPrincipiosActivos());
        btnLab.addActionListener(e -> mostrarLaboratorio());
        btnRecetas.addActionListener(e -> mostrarRecetas());
        btnBuscarPorPaciente.addActionListener(e -> buscarMedicamentosPorPaciente());
        btnBuscarLab.addActionListener(e -> buscarLaboratorio());
        btnBuscarPrincipio.addActionListener(e -> buscarPrincipioActivo());
    }
    
    // --- Métodos de Lógica de Consulta ---
    private void mostrarContraindicaciones() {
        List<Contraindicacion> lista = medicamentoService.listarContraindicaciones(medicamentoSeleccionado.getId());
        String resultado = lista.stream().map(c -> "- " + c.getDescripcion()).collect(Collectors.joining("\n"));
        areaResultados.setText("--- Contraindicaciones ---\n" + (resultado.isEmpty() ? "Ninguna." : resultado));
    }

    private void mostrarPrincipiosActivos() {
        List<PrincipioActivo> lista = medicamentoService.listarPrincipiosActivos(medicamentoSeleccionado.getId());
        String resultado = lista.stream().map(p -> "- " + p.getNombre()).collect(Collectors.joining("\n"));
        areaResultados.setText("--- Principios Activos ---\n" + (resultado.isEmpty() ? "Ninguno." : resultado));
    }

    private void mostrarLaboratorio() {
        Laboratorio lab = medicamentoService.obtenerLaboratorio(medicamentoSeleccionado.getIdLaboratorio());
        String resultado = "ID: " + lab.getId() + "\nNombre: " + lab.getNombre() + "\nDirección: " + lab.getDireccion();
        areaResultados.setText("--- Laboratorio ---\n" + resultado);
    }
    
    private void mostrarRecetas(){
        List<Receta> lista = medicamentoService.listarRecetasConMedicamento(medicamentoSeleccionado.getId());
        String resultado = lista.stream().map(r -> "- ID Receta: " + r.getId() + ", Fecha: " + r.getFecha()).collect(Collectors.joining("\n"));
        areaResultados.setText("--- Aparece en Recetas ---\n" + (resultado.isEmpty() ? "Ninguna." : resultado));
    }

    private void buscarMedicamentosPorPaciente() {
        try {
            int idPaciente = Integer.parseInt(txtBusquedaPaciente.getText());
            List<Medicamento> lista = medicamentoService.listarMedicamentosPorPaciente(idPaciente);
            String resultado = lista.stream().map(m -> String.format("- ID: %d, Nombre: %s, Unidades: %d", m.getId(), m.getNombre(), m.getUnidadesDisponibles())).collect(Collectors.joining("\n"));
            areaResultados.setText("--- Medicamentos para Paciente ID " + idPaciente + " ---\n" + (resultado.isEmpty() ? "Ninguno." : resultado));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de Paciente inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarLaboratorio() {
        String nombre = txtBusquedaAuxiliar.getText();
        Laboratorio lab = medicamentoService.buscarLaboratorioPorNombre(nombre);
        String resultado = (lab != null) ? "ID: " + lab.getId() + "\nNombre: " + lab.getNombre() + "\nDirección: " + lab.getDireccion() : "No encontrado.";
        areaResultados.setText("--- Búsqueda de Laboratorio ---\n" + resultado);
    }

    private void buscarPrincipioActivo() {
        String nombre = txtBusquedaAuxiliar.getText();
        PrincipioActivo pa = medicamentoService.buscarPrincipioActivoPorNombre(nombre);
        String resultado = (pa != null) ? "ID: " + pa.getId() + "\nNombre: " + pa.getNombre() : "No encontrado.";
        areaResultados.setText("--- Búsqueda de Principio Activo ---\n" + resultado);
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
            java.util.logging.Logger.getLogger(DialogoConsultasMedicamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasMedicamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasMedicamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasMedicamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

        /* Create and display the dialog */
    // Variables declaration - do not modify                     
    // End of variables declaration                   

