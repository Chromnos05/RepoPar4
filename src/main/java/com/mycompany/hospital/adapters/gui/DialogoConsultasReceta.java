/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.hospital.adapters.gui;
import com.mycompany.hospital.application.service.RecetaService;
import com.mycompany.hospital.domain.model.MedicamentoReceta;
import com.mycompany.hospital.domain.model.Receta;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 *
 * @author Danie
 */
public class DialogoConsultasReceta extends javax.swing.JDialog {

    /**
     * Creates new form DialogoConsultasReceta
     */
   private final RecetaService recetaService;
    private JTextArea areaResultados;
    private final Receta recetaSeleccionada;

    public DialogoConsultasReceta(Frame owner, RecetaService service, Receta receta) {
        super(owner, "Panel de Consultas de Receta", true);
        this.recetaService = service;
        this.recetaSeleccionada = receta;
        initUI();
    }

    private void initUI() {
        setSize(700, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panelBusquedas = createSearchPanel();
        tabbedPane.addTab("Búsquedas y Filtros", panelBusquedas);
        
        JPanel panelReportes = createReportPanel();
        tabbedPane.addTab("Reportes", panelReportes);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        
        add(tabbedPane, BorderLayout.NORTH);
        add(scrollResultados, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ver medicamentos de la receta seleccionada
        JButton btnVerMedicamentos = new JButton("Ver Medicamentos de la Receta Seleccionada");
        btnVerMedicamentos.setEnabled(recetaSeleccionada != null);
        btnVerMedicamentos.addActionListener(e -> mostrarMedicamentosDeReceta());
        panel.add(btnVerMedicamentos);
        panel.add(Box.createVerticalStrut(10));
        
        // Búsqueda por ID Médico
        panel.add(createInputPanel("ID Médico:", "Buscar por Médico", this::buscarPorMedico));
        // Búsqueda por ID Medicamento
        panel.add(createInputPanel("ID Medicamento:", "Buscar por Medicamento", this::buscarPorMedicamento));
        // Búsqueda por Mes
        JPanel panelMes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField txtAnio = new JTextField(4);
        JTextField txtMes = new JTextField(2);
        JButton btnBuscar = new JButton("Buscar por Mes");
        btnBuscar.addActionListener(e -> buscarPorMes(txtAnio.getText(), txtMes.getText()));
        panelMes.add(new JLabel("Año:"));
        panelMes.add(txtAnio);
        panelMes.add(new JLabel("Mes:"));
        panelMes.add(txtMes);
        panelMes.add(btnBuscar);
        panel.add(panelMes);
        
        return panel;
    }
    
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton btnReporte = new JButton("Generar Reporte: Recetas por Médico (Último Mes)");
        btnReporte.addActionListener(e -> generarReporte());
        panel.add(btnReporte);
        return panel;
    }

    private JPanel createInputPanel(String label, String buttonText, java.util.function.Consumer<String> action) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField textField = new JTextField(10);
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> action.accept(textField.getText()));
        panel.add(new JLabel(label));
        panel.add(textField);
        panel.add(button);
        return panel;
    }
    
    private void mostrarMedicamentosDeReceta() {
        if (recetaSeleccionada == null) return;
        List<MedicamentoReceta> medicamentos = recetaService.listarMedicamentosDeReceta(recetaSeleccionada.getId());
        String resultado = medicamentos.stream()
            .map(mr -> String.format("- ID Med: %d, Cant: %d, Dosis: %s", mr.getIdMedicamento(), mr.getCantidad(), mr.getDosis()))
            .collect(Collectors.joining("\n"));
        areaResultados.setText("--- Medicamentos en Receta ID " + recetaSeleccionada.getId() + " ---\n" + (resultado.isEmpty() ? "Ninguno." : resultado));
    }

    private void buscarPorMedico(String idStr) {
        try {
            List<Receta> recetas = recetaService.listarRecetasPorMedico(Integer.parseInt(idStr));
            String resultado = recetas.stream().map(r -> "ID: " + r.getId() + ", Fecha: " + r.getFecha()).collect(Collectors.joining("\n"));
            areaResultados.setText("--- Recetas del Médico ID " + idStr + " ---\n" + (resultado.isEmpty() ? "Ninguna." : resultado));
        } catch (NumberFormatException e) {
             areaResultados.setText("Error: ID de Médico inválido.");
        }
    }

    private void buscarPorMedicamento(String idStr) {
        try {
            List<Receta> recetas = recetaService.listarRecetasPorMedicamento(Integer.parseInt(idStr));
            String resultado = recetas.stream().map(r -> "ID: " + r.getId() + ", Fecha: " + r.getFecha()).collect(Collectors.joining("\n"));
            areaResultados.setText("--- Recetas con Medicamento ID " + idStr + " ---\n" + (resultado.isEmpty() ? "Ninguna." : resultado));
        } catch (NumberFormatException e) {
            areaResultados.setText("Error: ID de Medicamento inválido.");
        }
    }

    private void buscarPorMes(String anioStr, String mesStr) {
        try {
            List<Receta> recetas = recetaService.listarRecetasPorMes(Integer.parseInt(anioStr), Integer.parseInt(mesStr));
            String resultado = recetas.stream().map(r -> "ID: " + r.getId() + ", Fecha: " + r.getFecha()).collect(Collectors.joining("\n"));
            areaResultados.setText("--- Recetas de " + mesStr + "/" + anioStr + " ---\n" + (resultado.isEmpty() ? "Ninguna." : resultado));
        } catch (NumberFormatException e) {
             areaResultados.setText("Error: Año o Mes inválido.");
        }
    }

    private void generarReporte() {
        Map<String, Integer> reporte = recetaService.contarRecetasPorMedicoUltimoMes();
        String resultado = reporte.entrySet().stream()
            .map(entry -> "- " + entry.getKey() + ": " + entry.getValue() + " receta(s)")
            .collect(Collectors.joining("\n"));
        areaResultados.setText("--- Reporte: Recetas por Médico (Último Mes) ---\n" + (resultado.isEmpty() ? "No hay datos." : resultado));
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
            java.util.logging.Logger.getLogger(DialogoConsultasReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoConsultasReceta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
