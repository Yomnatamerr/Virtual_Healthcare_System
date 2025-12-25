import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Provider Panel - Healthcare provider interface with consultation management
 */
public class ProviderPanel extends JPanel {
    private DataManager dataManager;
    private VirtualCareGUI mainFrame;
    private Provider currentProvider;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    public ProviderPanel(DataManager dataManager, VirtualCareGUI mainFrame) {
        this.dataManager = dataManager;
        this.mainFrame = mainFrame;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(900, 80));

        JLabel titleLabel = new JLabel("  Healthcare Provider Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JButton backBtn = new JButton("← Back to Home");
        backBtn.setBackground(new Color(25, 118, 210));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> mainFrame.showWelcome());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backBtn, BorderLayout.EAST);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Login", createLoginPanel());
        tabbedPane.addTab("My Appointments", createAppointmentsPanel());
        tabbedPane.addTab("Manage Consultations", createConsultationPanel());
        tabbedPane.addTab("Issue Prescription", createPrescriptionPanel());

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Provider Login");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Provider ID:"), gbc);

        JTextField idField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(33, 150, 243));
        loginBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            try {
                String providerId = idField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (providerId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both ID and password!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Provider provider = dataManager.searchProviderById(providerId);
                if (provider != null) {
                    if (provider.getPassword().equals(password)) {
                        currentProvider = provider;
                        JOptionPane.showMessageDialog(this, "Welcome, Dr. " + provider.getName() + "!");
                        refreshAppointmentTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect password!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Provider ID not found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        return panel;
    }

    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Appointments");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        String[] columns = {"Appointment ID", "Patient ID", "Date/Time", "Type", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        appointmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshBtn = new JButton("Refresh");
        JButton completeBtn = new JButton("Mark as Completed");

        refreshBtn.addActionListener(e -> refreshAppointmentTable());

        completeBtn.addActionListener(e -> {
            int selectedRow = appointmentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an appointment!");
                return;
            }

            try {
                String aptId = (String) tableModel.getValueAt(selectedRow, 0);
                List<Appointment> appointments = dataManager.loadAppointments();
                for (Appointment apt : appointments) {
                    if (apt.getAppointmentId().equals(aptId)) {
                        apt.setStatus("COMPLETED");
                        break;
                    }
                }
                dataManager.saveAppointments(appointments);
                refreshAppointmentTable();
                JOptionPane.showMessageDialog(this, "Appointment marked as completed!");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        buttonPanel.add(refreshBtn);
        buttonPanel.add(completeBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshAppointmentTable() {
        if (currentProvider == null) return;

        tableModel.setRowCount(0);
        try {
            List<Appointment> appointments = dataManager.searchAppointmentsByProvider(currentProvider.getUserId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Appointment apt : appointments) {
                tableModel.addRow(new Object[]{
                        apt.getAppointmentId(),
                        apt.getPatientId(),
                        apt.getDateTime().format(formatter),
                        apt.getConsultationType(),
                        apt.getStatus()
                });
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private JPanel createConsultationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Update Patient EHR");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Patient ID:"), gbc);

        JTextField patientIdField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(patientIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Diagnosis:"), gbc);

        JTextArea diagnosisArea = new JTextArea(3, 20);
        JScrollPane diagScroll = new JScrollPane(diagnosisArea);
        gbc.gridx = 1;
        panel.add(diagScroll, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Treatment:"), gbc);

        JTextArea treatmentArea = new JTextArea(3, 20);
        JScrollPane treatScroll = new JScrollPane(treatmentArea);
        gbc.gridx = 1;
        panel.add(treatScroll, gbc);

        JButton updateBtn = new JButton("Update EHR");
        updateBtn.setBackground(new Color(33, 150, 243));
        updateBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(updateBtn, gbc);

        updateBtn.addActionListener(e -> {
            if (currentProvider == null) {
                JOptionPane.showMessageDialog(this, "Please login first!");
                return;
            }

            try {
                String patientId = patientIdField.getText();
                String diagnosis = diagnosisArea.getText();
                String treatment = treatmentArea.getText();

                List<EHR> ehrs = dataManager.loadEHRs();
                boolean found = false;
                for (EHR ehr : ehrs) {
                    if (ehr.getPatientId().equals(patientId)) {
                        if (!diagnosis.isEmpty()) ehr.addDiagnosis(diagnosis);
                        if (!treatment.isEmpty()) ehr.addTreatment(treatment);
                        found = true;
                        break;
                    }
                }

                if (found) {
                    dataManager.saveEHRs(ehrs);
                    JOptionPane.showMessageDialog(this, "EHR updated successfully!");
                    diagnosisArea.setText("");
                    treatmentArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Patient not found!");
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel createPrescriptionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Issue Electronic Prescription");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Patient ID:"), gbc);

        JTextField patientIdField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(patientIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Medication:"), gbc);

        JTextField medicationField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(medicationField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Dosage:"), gbc);

        JTextField dosageField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(dosageField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Frequency:"), gbc);

        JTextField frequencyField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(frequencyField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Instructions:"), gbc);

        JTextArea instructionsArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(instructionsArea);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        JButton issueBtn = new JButton("Issue Prescription");
        issueBtn.setBackground(new Color(33, 150, 243));
        issueBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(issueBtn, gbc);

        issueBtn.addActionListener(e -> {
            if (currentProvider == null) {
                JOptionPane.showMessageDialog(this, "Please login first!");
                return;
            }

            try {
                Prescription prescription = new Prescription(
                        patientIdField.getText(),
                        currentProvider.getUserId(),
                        medicationField.getText(),
                        dosageField.getText(),
                        frequencyField.getText(),
                        instructionsArea.getText()
                );

                List<Prescription> prescriptions = dataManager.loadPrescriptions();
                prescriptions.add(prescription);
                dataManager.savePrescriptions(prescriptions);

                // Add to EHR
                List<EHR> ehrs = dataManager.loadEHRs();
                for (EHR ehr : ehrs) {
                    if (ehr.getPatientId().equals(patientIdField.getText())) {
                        ehr.addPrescription(prescription.getPrescriptionId());
                        break;
                    }
                }
                dataManager.saveEHRs(ehrs);

                JOptionPane.showMessageDialog(this, "Prescription issued successfully!\nRx ID: " +
                        prescription.getPrescriptionId());

                // Clear fields
                patientIdField.setText("");
                medicationField.setText("");
                dosageField.setText("");
                frequencyField.setText("");
                instructionsArea.setText("");

            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }
}