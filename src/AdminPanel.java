import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Admin Panel - Administrator interface for system management
 */
public class AdminPanel extends JPanel {
    private DataManager dataManager;
    private VirtualCareGUI mainFrame;
    private Administrator currentAdmin;

    public AdminPanel(DataManager dataManager, VirtualCareGUI mainFrame) {
        this.dataManager = dataManager;
        this.mainFrame = mainFrame;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(156, 39, 176));
        headerPanel.setPreferredSize(new Dimension(900, 80));

        JLabel titleLabel = new JLabel("  Administrator Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);

        JButton backBtn = new JButton("← Back to Home");
        backBtn.setBackground(new Color(123, 31, 162));
        backBtn.setForeground(Color.BLACK);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> mainFrame.showWelcome());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backBtn, BorderLayout.EAST);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Login", createLoginPanel());
        tabbedPane.addTab("Manage Providers", createProviderManagementPanel());
        tabbedPane.addTab("View Patients", createPatientViewPanel());
        tabbedPane.addTab("System Analytics", createAnalyticsPanel());

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Administrator Login");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Admin ID:"), gbc);

        JTextField idField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(156, 39, 176));
        loginBtn.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            try {
                String adminId = idField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (adminId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both ID and password!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<Administrator> admins = dataManager.loadAdministrators();
                boolean found = false;
                for (Administrator admin : admins) {
                    if (admin != null && admin.getUserId() != null &&
                            admin.getUserId().trim().equals(adminId)) {
                        if (admin.getPassword() != null && admin.getPassword().equals(password)) {
                            currentAdmin = admin;
                            JOptionPane.showMessageDialog(this, "Welcome, " + admin.getName() + "!");
                            found = true;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(this, "Incorrect password!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(this, "Admin ID not found!\n" +
                                    "Please register a new admin account below.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Quick Register Section
        JSeparator separator = new JSeparator();
        gbc.gridy = 4;
        panel.add(separator, gbc);

        JLabel regTitle = new JLabel("Register New Admin");
        regTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 5;
        panel.add(regTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Name:"), gbc);
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(new JLabel("Phone:"), gbc);
        JTextField phoneField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        panel.add(new JLabel("Password:"), gbc);
        JPasswordField newPassField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(newPassField, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        panel.add(new JLabel("Department:"), gbc);
        JTextField deptField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(deptField, gbc);

        JButton registerBtn = new JButton("Register Admin");
        registerBtn.setBackground(new Color(156, 39, 176));
        registerBtn.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 2;
        panel.add(registerBtn, gbc);

        registerBtn.addActionListener(e -> {
            try {
                Administrator newAdmin = new Administrator(
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText(),
                        new String(newPassField.getPassword()),
                        deptField.getText()
                );

                List<Administrator> admins = dataManager.loadAdministrators();
                admins.add(newAdmin);
                dataManager.saveAdministrators(admins);

                JOptionPane.showMessageDialog(this, "Admin registered! ID: " + newAdmin.getUserId());
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                newPassField.setText("");
                deptField.setText("");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel createProviderManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add Provider Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add New Healthcare Provider");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);

        JTextField phoneField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Password:"), gbc);

        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        formPanel.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Specialty:"), gbc);

        String[] specialties = {"Cardiology", "Dermatology", "Pediatrics", "Orthopedics",
                "General Medicine", "Neurology", "Psychiatry"};
        JComboBox<String> specialtyCombo = new JComboBox<>(specialties);
        gbc.gridx = 1;
        formPanel.add(specialtyCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Availability:"), gbc);

        JTextField availField = new JTextField(20);
        availField.setText("Mon-Fri 9AM-5PM");
        gbc.gridx = 1;
        formPanel.add(availField, gbc);

        JButton addBtn = new JButton("Add Provider");
        addBtn.setBackground(new Color(156, 39, 176));
        addBtn.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        formPanel.add(addBtn, gbc);

        // Provider List
        String[] columns = {"Provider ID", "Name", "Specialty", "Email", "Availability"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable providerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(providerTable);

        JButton refreshBtn = new JButton("Refresh List");
        refreshBtn.addActionListener(e -> {
            tableModel.setRowCount(0);
            try {
                List<Provider> providers = dataManager.loadProviders();
                for (Provider provider : providers) {
                    tableModel.addRow(new Object[]{
                            provider.getUserId(),
                            provider.getName(),
                            provider.getSpecialty(),
                            provider.getEmail(),
                            provider.getAvailability()
                    });
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        addBtn.addActionListener(e -> {
            if (currentAdmin == null) {
                JOptionPane.showMessageDialog(this, "Please login first!");
                return;
            }

            try {
                Provider newProvider = new Provider(
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText(),
                        new String(passField.getPassword()),
                        (String) specialtyCombo.getSelectedItem(),
                        availField.getText()
                );

                List<Provider> providers = dataManager.loadProviders();
                providers.add(newProvider);
                dataManager.saveProviders(providers);

                JOptionPane.showMessageDialog(this, "Provider added successfully!\nProvider ID: " +
                        newProvider.getUserId());

                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                passField.setText("");

                refreshBtn.doClick();

            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(refreshBtn, BorderLayout.CENTER);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPatientViewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Registered Patients");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        String[] columns = {"Patient ID", "Name", "Email", "Phone", "Emergency Contact"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);

        JButton loadBtn = new JButton("Load Patients");
        loadBtn.addActionListener(e -> {
            tableModel.setRowCount(0);
            try {
                List<Patient> patients = dataManager.loadPatients();
                for (Patient patient : patients) {
                    tableModel.addRow(new Object[]{
                            patient.getUserId(),
                            patient.getName(),
                            patient.getEmail(),
                            patient.getPhone(),
                            patient.getEmergencyContact()
                    });
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(loadBtn, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAnalyticsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel title = new JLabel("System Analytics");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        JTextArea analyticsArea = new JTextArea(20, 50);
        analyticsArea.setEditable(false);
        analyticsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(analyticsArea);
        gbc.gridy = 1; gbc.weightx = 1.0; gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        JButton generateBtn = new JButton("Generate Analytics Report");
        generateBtn.setBackground(new Color(156, 39, 176));
        generateBtn.setForeground(Color.BLACK);
        gbc.gridy = 2; gbc.weighty = 0;
        panel.add(generateBtn, gbc);

        generateBtn.addActionListener(e -> {
            try {
                StringBuilder report = new StringBuilder();
                report.append("=== VIRTUALCARE 2025 SYSTEM ANALYTICS ===\n\n");

                List<Patient> patients = dataManager.loadPatients();
                List<Provider> providers = dataManager.loadProviders();
                List<Appointment> appointments = dataManager.loadAppointments();
                List<Prescription> prescriptions = dataManager.loadPrescriptions();
                List<Bill> bills = dataManager.loadBills();

                report.append("Total Patients: ").append(patients.size()).append("\n");
                report.append("Total Providers: ").append(providers.size()).append("\n");
                report.append("Total Appointments: ").append(appointments.size()).append("\n");
                report.append("Total Prescriptions: ").append(prescriptions.size()).append("\n");
                report.append("Total Bills: ").append(bills.size()).append("\n\n");

                // Appointment status breakdown
                int scheduled = 0, completed = 0, cancelled = 0;
                for (Appointment apt : appointments) {
                    switch (apt.getStatus()) {
                        case "SCHEDULED": scheduled++; break;
                        case "COMPLETED": completed++; break;
                        case "CANCELLED": cancelled++; break;
                    }
                }

                report.append("--- Appointment Status ---\n");
                report.append("Scheduled: ").append(scheduled).append("\n");
                report.append("Completed: ").append(completed).append("\n");
                report.append("Cancelled: ").append(cancelled).append("\n\n");

                // Provider specialties
                report.append("--- Providers by Specialty ---\n");
                java.util.Map<String, Integer> specialtyCount = new java.util.HashMap<>();
                for (Provider provider : providers) {
                    specialtyCount.put(provider.getSpecialty(),
                            specialtyCount.getOrDefault(provider.getSpecialty(), 0) + 1);
                }
                for (java.util.Map.Entry<String, Integer> entry : specialtyCount.entrySet()) {
                    report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }

                analyticsArea.setText(report.toString());

            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }
}