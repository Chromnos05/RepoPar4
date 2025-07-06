package com.mycompany.hospital;

import com.mycompany.hospital.adapters.gui.EmpleadoForm;
import com.mycompany.hospital.adapters.gui.EnfermedadForm;
import com.mycompany.hospital.adapters.gui.InicioPanel;
import com.mycompany.hospital.adapters.gui.MedicamentoForm;
import com.mycompany.hospital.adapters.gui.PacienteForm;
import com.mycompany.hospital.adapters.gui.RecetaForm;
import com.mycompany.hospital.application.service.PacienteService;
import com.mycompany.hospital.application.usecase.PacienteServiceImpl;
import com.mycompany.hospital.domain.repository.PacienteRepository;
import com.mycompany.hospital.infrastructure.postgresql.PacienteRepositoryPostgres;

import com.mycompany.hospital.application.service.EmpleadoService;
import com.mycompany.hospital.application.usecase.EmpleadoServiceImpl;
import com.mycompany.hospital.domain.repository.EmpleadoRepository;
import com.mycompany.hospital.infrastructure.postgresql.EmpleadoRepositoryPostgres;

import com.mycompany.hospital.application.service.MedicamentoService;
import com.mycompany.hospital.application.usecase.MedicamentoServiceImpl;
import com.mycompany.hospital.domain.repository.MedicamentoRepository;
import com.mycompany.hospital.infrastructure.postgresql.MedicamentoRepositoryPostgres;

import com.mycompany.hospital.application.service.RecetaService;
import com.mycompany.hospital.application.usecase.RecetaServiceImpl;
import com.mycompany.hospital.domain.repository.RecetaRepository;
import com.mycompany.hospital.infrastructure.postgresql.RecetaRepositoryPostgres;

// --- Imports para Enfermedad --- // NUEVO
import com.mycompany.hospital.application.service.EnfermedadService;
import com.mycompany.hospital.application.usecase.EnfermedadServiceImpl;
import com.mycompany.hospital.domain.repository.EnfermedadRepository;
import com.mycompany.hospital.infrastructure.postgresql.EnfermedadRepositoryPostgres;
// --- Fin imports para Enfermedad ---

import com.mycompany.hospital.adapters.gui.MedicoForm;
import com.mycompany.hospital.adapters.gui.CitaMedicaForm;
import com.mycompany.hospital.adapters.gui.DiagnosticoForm;

import com.mycompany.hospital.application.service.MedicoService;
import com.mycompany.hospital.application.usecase.MedicoServiceImpl;
import com.mycompany.hospital.domain.repository.MedicoRepository;
import com.mycompany.hospital.infrastructure.postgresql.MedicoRepositoryPostgres;

import com.mycompany.hospital.application.service.CitaMedicaService;
import com.mycompany.hospital.application.usecase.CitaMedicaServiceImpl;
import com.mycompany.hospital.domain.repository.CitaMedicaRepository;
import com.mycompany.hospital.infrastructure.postgresql.CitaMedicaRepositoryPostgres;

import com.mycompany.hospital.application.service.DiagnosticoService;
import com.mycompany.hospital.application.usecase.DiagnosticoServiceImpl;
import com.mycompany.hospital.domain.repository.DiagnosticoRepository;
import com.mycompany.hospital.infrastructure.postgresql.DiagnosticoRepositoryPostgres;


import javax.swing.*;
import java.awt.*;

public class Main extends javax.swing.JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    // --- Formularios ---
    private final PacienteForm pacienteForm;
    private final EmpleadoForm empleadoForm;
    private final MedicamentoForm medicamentoForm;
    private final RecetaForm recetaForm;
    private final EnfermedadForm enfermedadForm; // NUEVO
    private final MedicoForm medicoForm;
    private final CitaMedicaForm citaForm;
    private final DiagnosticoForm diagnosticoForm;

    public Main() {
        // --- Servicios ---
        PacienteRepository pacienteRepo = new PacienteRepositoryPostgres();
        PacienteService pacienteService = new PacienteServiceImpl(pacienteRepo);
        pacienteForm = new PacienteForm(pacienteService, cardLayout, contentPanel);
        
        EmpleadoRepository empleadoRepo = new EmpleadoRepositoryPostgres();
        EmpleadoService empleadoService = new EmpleadoServiceImpl(empleadoRepo);
        empleadoForm = new EmpleadoForm(empleadoService, cardLayout, contentPanel);
        
        MedicamentoRepository medicamentoRepo = new MedicamentoRepositoryPostgres();
        MedicamentoService medicamentoService = new MedicamentoServiceImpl(medicamentoRepo);
        medicamentoForm = new MedicamentoForm(medicamentoService, cardLayout, contentPanel);
        
        RecetaRepository recetaRepo = new RecetaRepositoryPostgres();
        RecetaService recetaService = new RecetaServiceImpl(recetaRepo);
        recetaForm = new RecetaForm(recetaService, cardLayout, contentPanel);
        
        // --- Servicio Enfermedad --- // NUEVO
        EnfermedadRepository enfermedadRepo = new EnfermedadRepositoryPostgres();
        EnfermedadService enfermedadService = new EnfermedadServiceImpl(enfermedadRepo);
        enfermedadForm = new EnfermedadForm(enfermedadService, cardLayout, contentPanel);
        // --- Fin Servicio Enfermedad ---
        
        MedicoRepository medicoRepo = new MedicoRepositoryPostgres();
        MedicoService medicoService = new MedicoServiceImpl(medicoRepo);
        medicoForm = new MedicoForm(medicoService, cardLayout, contentPanel);

        CitaMedicaRepository citaRepo = new CitaMedicaRepositoryPostgres();
        CitaMedicaService citaService = new CitaMedicaServiceImpl(citaRepo);
        citaForm = new CitaMedicaForm(citaService, cardLayout, contentPanel);

        DiagnosticoRepository diagnosticoRepo = new DiagnosticoRepositoryPostgres();
        DiagnosticoService diagnosticoService = new DiagnosticoServiceImpl(diagnosticoRepo);
        diagnosticoForm = new DiagnosticoForm(diagnosticoService, cardLayout, contentPanel);

        initUI();
    }

    private void initUI() {
        setTitle("Sistema Hospitalario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(44, 62, 80));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));

        // --- Botones del Menú ---
        JButton btnPacientes = crearBotonMenu("Pacientes");
        JButton btnMedicos = crearBotonMenu("Médicos");
        JButton btnEmpleados = crearBotonMenu("Empleados");
        JButton btnMedicamentos = crearBotonMenu("Medicamentos");
        JButton btnRecetas = crearBotonMenu("Recetas");
        JButton btnEnfermedades = crearBotonMenu("Enfermedades"); // NUEVO
        JButton btnCita = crearBotonMenu("Citas Médicas");
        JButton btnDiagnostico = crearBotonMenu("Diagnósticos");
        JButton btnSalir = crearBotonMenu("Salir");

        // --- Action Listeners ---
        btnPacientes.addActionListener(e -> mostrarPanel("pacientes"));
        btnMedicos.addActionListener(e -> cardLayout.show(contentPanel, "medico"));
        btnEmpleados.addActionListener(e -> mostrarPanel("empleados"));
        btnMedicamentos.addActionListener(e -> mostrarPanel("medicamentos"));
        btnRecetas.addActionListener(e -> mostrarPanel("recetas"));
        btnEnfermedades.addActionListener(e -> mostrarPanel("enfermedades")); // NUEVO
        btnCita.addActionListener(e -> cardLayout.show(contentPanel, "cita"));
        btnDiagnostico.addActionListener(e -> cardLayout.show(contentPanel, "diagnostico"));
        btnSalir.addActionListener(e -> {
            UIManager.put("OptionPane.yesButtonText", "Sí");
            UIManager.put("OptionPane.noButtonText", "No");

            ImageIcon icono = new ImageIcon("src/main/resources/iconos/salir.png");
            Image imagen = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon iconoRedimensionado = new ImageIcon(imagen);
            JLabel mensaje = new JLabel("¿Deseas salir?");
            mensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            mensaje.setIcon(iconoRedimensionado);
            mensaje.setIconTextGap(15);
            mensaje.setHorizontalAlignment(SwingConstants.CENTER);

            int confirmacion = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // --- Añadir botones al panel ---
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(btnPacientes);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnMedicos);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnEmpleados);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnMedicamentos);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnRecetas);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnEnfermedades); // NUEVO
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnCita);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnDiagnostico);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(btnSalir);
        menuPanel.add(Box.createVerticalStrut(20));
        
        // --- Añadir formularios al panel de contenido ---
        contentPanel.add(new InicioPanel(), "inicio");
        contentPanel.add(pacienteForm, "pacientes");
        contentPanel.add(empleadoForm, "empleados");
        contentPanel.add(medicamentoForm, "medicamentos");
        contentPanel.add(recetaForm, "recetas");
        contentPanel.add(enfermedadForm, "enfermedades"); // NUEVO
        contentPanel.add(medicoForm, "medico");
        contentPanel.add(citaForm, "cita");
        contentPanel.add(diagnosticoForm, "diagnostico");

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "inicio");
        
        setVisible(true);
    }
    
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void mostrarPanel(String nombre) {
        cardLayout.show(contentPanel, nombre);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

    public static void main(String args[]) {

        SwingUtilities.invokeLater(Main::new);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
