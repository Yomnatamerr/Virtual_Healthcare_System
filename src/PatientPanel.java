import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Patient Panel - Provides patient interface with CRUD operations
 */
public class PatientPanel extends JPanel {
    private DataManager dataManager;
    private VirtualCareGUI mainFrame;
    private Patient currentPatient;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    public PatientPanel(DataManager dataManager, VirtualCareGUI mainFrame) {
        this.dataManager = dataManager;
        this.mainFrame = mainFrame;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(76, 175, 80));
        headerPanel.setPreferredSize(new Dimension(900, 80));

        JLabel titleLabel = new JLabel("  Patient Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JButton backBtn = new JButton("← Back to Home");
        backBtn.setBackground(new Color(56, 142, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> mainFrame.showWelcome());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backBtn, BorderLayout.EAST);

        // Main content with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        tabbedPane.addTab("Login/Register", createLoginRegisterPanel());
        tabbedPane.addTab("Book Appointment", createAppointmentPanel());
        tabbedPane.addTab("My Appointments", createMyAppointmentsPanel());
        tabbedPane.addTab("Health Records", createHealthRecordsPanel());
        tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());
        tabbedPane.addTab("Bills & Payments", createBillingPanel());

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createLoginRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Login Section
        JLabel loginTitle = new JLabel("Patient Login");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(loginTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Patient ID:"), gbc);

        JTextField loginIdField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(loginIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        JPasswordField loginPassField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(loginPassField, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            try {
                String patientId = loginIdField.getText().trim();
                String password = new String(loginPassField.getPassword()).trim();

                if (patientId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both ID and password!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Patient patient = dataManager.searchPatientById(patientId);
                if (patient != null) {
                    if (patient.getPassword().equals(password)) {
                        currentPatient = patient;
                        JOptionPane.showMessageDialog(this, "Welcome, " + patient.getName() + "!");
                        refreshAppointmentTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect password!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Patient ID not found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Register Section
        JSeparator separator = new JSeparator();
        gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(separator, gbc);

        JLabel registerTitle = new JLabel("New Patient Registration");
        registerTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 5;
        panel.add(registerTitle, gbc);

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
        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        panel.add(new JLabel("Medical History:"), gbc);
        JTextArea medHistoryArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(medHistoryArea);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        panel.add(new JLabel("Emergency Contact:"), gbc);
        JTextField emergencyField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emergencyField, gbc);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(33, 150, 243));
        registerBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 12; gbc.gridwidth = 2;
        panel.add(registerBtn, gbc);

        registerBtn.addActionListener(e -> {
            try {
                Patient newPatient = new Patient(
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText(),
                        new String(passField.getPassword()),
                        medHistoryArea.getText(),
                        emergencyField.getText()
                );

                List<Patient> patients = dataManager.loadPatients();
                patients.add(newPatient);
                dataManager.savePatients(patients);

                // Create EHR for patient
                EHR ehr = new EHR(newPatient.getEhrId(), newPatient.getUserId());
                List<EHR> ehrs = dataManager.loadEHRs();
                ehrs.add(ehr);
                dataManager.saveEHRs(ehrs);

                JOptionPane.showMessageDialog(this,
                        "Registration successful!\nYour Patient ID is: " + newPatient.getUserId());

                // Clear fields
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                passField.setText("");
                medHistoryArea.setText("");
                emergencyField.setText("");

            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createBillingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Bills & Payments");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        // Bills Table
        String[] columns = {"Bill ID", "Appointment ID", "Amount", "Date", "Status", "Description"};
        DefaultTableModel billTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable billTable = new JTable(billTableModel);
        JScrollPane scrollPane = new JScrollPane(billTable);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Billing Summary"));

        JLabel totalBillsLabel = new JLabel("Total Bills: 0");
        JLabel unpaidAmountLabel = new JLabel("Unpaid Amount: $0.00");
        JLabel paidAmountLabel = new JLabel("Paid Amount: $0.00");

        totalBillsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        unpaidAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        unpaidAmountLabel.setForeground(new Color(211, 47, 47));
        paidAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        paidAmountLabel.setForeground(new Color(76, 175, 80));

        summaryPanel.add(totalBillsLabel);
        summaryPanel.add(new JLabel(""));
        summaryPanel.add(unpaidAmountLabel);
        summaryPanel.add(paidAmountLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton loadBtn = new JButton("Load Bills");
        loadBtn.setBackground(new Color(33, 150, 243));
        loadBtn.setForeground(Color.WHITE);

        JButton payBtn = new JButton("Pay Selected Bill");
        payBtn.setBackground(new Color(76, 175, 80));
        payBtn.setForeground(Color.WHITE);

        JButton viewDetailsBtn = new JButton("View Details");
        viewDetailsBtn.setBackground(new Color(156, 39, 176));
        viewDetailsBtn.setForeground(Color.WHITE);

        loadBtn.addActionListener(e -> {
            if (currentPatient == null) {
                JOptionPane.showMessageDialog(this, "Please login first!");
                return;
            }

            billTableModel.setRowCount(0);
            try {
                List<Bill> bills = dataManager.loadBills();
                int totalBills = 0;
                double unpaidAmount = 0.0;
                double paidAmount = 0.0;

                for (Bill bill : bills) {
                    if (bill.getPatientId().equals(currentPatient.getUserId())) {
                        billTableModel.addRow(new Object[]{
                                bill.getBillId(),
                                bill.getAppointmentId(),
                                String.format("$%.2f", bill.getAmount()),
                                bill.getBillDate(),
                                bill.getStatus(),
                                bill.getDescription()
                        });

                        totalBills++;
                        if (bill.getStatus().equals("UNPAID")) {
                            unpaidAmount += bill.getAmount();
                        } else {
                            paidAmount += bill.getAmount();
                        }
                    }
                }

                totalBillsLabel.setText("Total Bills: " + totalBills);
                unpaidAmountLabel.setText(String.format("Unpaid Amount: $%.2f", unpaidAmount));
                paidAmountLabel.setText(String.format("Paid Amount: $%.2f", paidAmount));

            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading bills: " + ex.getMessage());
            }
        });

        payBtn.addActionListener(e -> {
            int selectedRow = billTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a bill to pay!");
                return;
            }

            String billId = (String) billTableModel.getValueAt(selectedRow, 0);
            String status = (String) billTableModel.getValueAt(selectedRow, 4);

            if (status.equals("PAID")) {
                JOptionPane.showMessageDialog(this, "This bill has already been paid!");
                return;
            }

            try {
                List<Bill> bills = dataManager.loadBills();
                Bill selectedBill = null;

                for (Bill bill : bills) {
                    if (bill.getBillId().equals(billId)) {
                        selectedBill = bill;
                        break;
                    }
                }

                if (selectedBill != null) {
                    // Payment confirmation dialog
                    String[] paymentMethods = {"Credit Card", "Debit Card", "PayPal", "Insurance"};
                    String method = (String) JOptionPane.showInputDialog(
                            this,
                            "Select Payment Method for $" + selectedBill.getAmount() + ":",
                            "Payment",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            paymentMethods,
                            paymentMethods[0]
                    );

                    if (method != null) {
                        // Update bill status
                        selectedBill.setStatus("PAID");
                        dataManager.saveBills(bills);

                        // Create payment record
                        Payment payment = new Payment(
                                selectedBill.getBillId(),
                                currentPatient.getUserId(),
                                selectedBill.getAmount(),
                                method
                        );

                        List<Payment> payments = dataManager.loadPayments();
                        payments.add(payment);
                        dataManager.savePayments(payments);

                        JOptionPane.showMessageDialog(this,
                                "Payment Successful!\n" +
                                        "Payment ID: " + payment.getPaymentId() + "\n" +
                                        "Bill ID: " + selectedBill.getBillId() + "\n" +
                                        "Amount Paid: $" + selectedBill.getAmount() + "\n" +
                                        "Payment Method: " + method + "\n" +
                                        "Transaction Date: " + payment.getPaymentDate().toLocalDate() + "\n" +
                                        "Thank you for your payment!");

                        loadBtn.doClick(); // Refresh the table
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error processing payment: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        viewDetailsBtn.addActionListener(e -> {
            int selectedRow = billTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a bill to view details!");
                return;
            }

            String billId = (String) billTableModel.getValueAt(selectedRow, 0);
            String appointmentId = (String) billTableModel.getValueAt(selectedRow, 1);
            String amount = (String) billTableModel.getValueAt(selectedRow, 2);
            String date = billTableModel.getValueAt(selectedRow, 3).toString();
            String status = (String) billTableModel.getValueAt(selectedRow, 4);
            String description = (String) billTableModel.getValueAt(selectedRow, 5);

            String details = "═══════════════════════════════════\n" +
                    "           BILL DETAILS\n" +
                    "═══════════════════════════════════\n\n" +
                    "Bill ID: " + billId + "\n" +
                    "Appointment ID: " + appointmentId + "\n" +
                    "Description: " + description + "\n" +
                    "Amount: " + amount + "\n" +
                    "Date Issued: " + date + "\n" +
                    "Payment Status: " + status + "\n\n" +
                    "Patient: " + currentPatient.getName() + "\n" +
                    "Patient ID: " + currentPatient.getUserId() + "\n\n" +
                    "═══════════════════════════════════\n" +
                    "For questions, contact billing dept.";

            JTextArea textArea = new JTextArea(details);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane detailScroll = new JScrollPane(textArea);
            detailScroll.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(this, detailScroll, "Bill Details", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(loadBtn);
        buttonPanel.add(payBtn);
        buttonPanel.add(viewDetailsBtn);

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(summaryPanel, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Book New Appointment");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Select Provider:"), gbc);

        JComboBox<String> providerCombo = new JComboBox<>();
        try {
            List<Provider> providers = dataManager.loadProviders();
            for (Provider provider : providers) {
                providerCombo.addItem(provider.getUserId() + " - " + provider.getName() +
                        " (" + provider.getSpecialty() + ")");
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        gbc.gridx = 1;
        formPanel.add(providerCombo, gbc);

        // Date Picker
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Select Date:"), gbc);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Year
        JComboBox<Integer> yearCombo = new JComboBox<>();
        int currentYear = java.time.Year.now().getValue();
        for (int i = currentYear; i <= currentYear + 2; i++) {
            yearCombo.addItem(i);
        }

        // Month
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthCombo = new JComboBox<>(months);
        monthCombo.setSelectedIndex(java.time.LocalDate.now().getMonthValue() - 1);

        // Day
        JComboBox<Integer> dayCombo = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayCombo.addItem(i);
        }
        dayCombo.setSelectedItem(java.time.LocalDate.now().getDayOfMonth());

        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearCombo);
        datePanel.add(new JLabel("Month:"));
        datePanel.add(monthCombo);
        datePanel.add(new JLabel("Day:"));
        datePanel.add(dayCombo);

        gbc.gridx = 1;
        formPanel.add(datePanel, gbc);

        // Time Picker
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Select Time:"), gbc);

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Hour
        JComboBox<Integer> hourCombo = new JComboBox<>();
        for (int i = 8; i <= 18; i++) {
            hourCombo.addItem(i);
        }
        hourCombo.setSelectedItem(9);

        // Minute
        JComboBox<String> minuteCombo = new JComboBox<>();
        minuteCombo.addItem("00");
        minuteCombo.addItem("15");
        minuteCombo.addItem("30");
        minuteCombo.addItem("45");

        timePanel.add(new JLabel("Hour:"));
        timePanel.add(hourCombo);
        timePanel.add(new JLabel("Minute:"));
        timePanel.add(minuteCombo);
        timePanel.add(new JLabel("(8 AM - 6 PM)"));

        gbc.gridx = 1;
        formPanel.add(timePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Consultation Type:"), gbc);

        String[] types = {"General Consultation", "Follow-up", "Specialist Consultation", "Emergency"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        gbc.gridx = 1;
        formPanel.add(typeCombo, gbc);

        JButton bookBtn = new JButton("Book Appointment");
        bookBtn.setBackground(new Color(76, 175, 80));
        bookBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(bookBtn, gbc);

        bookBtn.addActionListener(e -> {
            if (currentPatient == null) {
                JOptionPane.showMessageDialog(this, "Please login first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String providerStr = (String) providerCombo.getSelectedItem();
                String providerId = providerStr.split(" - ")[0];

                // Build LocalDateTime from selections
                int year = (Integer) yearCombo.getSelectedItem();
                int month = monthCombo.getSelectedIndex() + 1;
                int day = (Integer) dayCombo.getSelectedItem();
                int hour = (Integer) hourCombo.getSelectedItem();
                int minute = Integer.parseInt((String) minuteCombo.getSelectedItem());

                LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);

                // Check if date is in the past
                if (dateTime.isBefore(LocalDateTime.now())) {
                    JOptionPane.showMessageDialog(this, "Cannot book appointments in the past!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Appointment appointment = new Appointment(
                        currentPatient.getUserId(),
                        providerId,
                        dateTime,
                        (String) typeCombo.getSelectedItem()
                );

                List<Appointment> appointments = dataManager.loadAppointments();
                appointments.add(appointment);
                dataManager.saveAppointments(appointments);

                // Create a bill for the appointment
                double amount = 0;
                String consultType = (String) typeCombo.getSelectedItem();
                switch (consultType) {
                    case "General Consultation": amount = 100.00; break;
                    case "Follow-up": amount = 75.00; break;
                    case "Specialist Consultation": amount = 150.00; break;
                    case "Emergency": amount = 200.00; break;
                }

                Bill bill = new Bill(currentPatient.getUserId(), appointment.getAppointmentId(),
                        amount, consultType);
                List<Bill> bills = dataManager.loadBills();
                bills.add(bill);
                dataManager.saveBills(bills);

                JOptionPane.showMessageDialog(this,
                        "Appointment booked successfully!\n" +
                                "Appointment ID: " + appointment.getAppointmentId() + "\n" +
                                "Date: " + dateTime.toLocalDate() + "\n" +
                                "Time: " + dateTime.toLocalTime() + "\n" +
                                "Bill Amount: $" + amount);

                refreshAppointmentTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error booking appointment: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMyAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Appointments");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        String[] columns = {"Appointment ID", "Provider ID", "Date/Time", "Type", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        appointmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshAppointmentTable());

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshAppointmentTable() {
        if (currentPatient == null) return;

        tableModel.setRowCount(0);
        try {
            List<Appointment> appointments = dataManager.searchAppointmentsByPatient(currentPatient.getUserId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Appointment apt : appointments) {
                tableModel.addRow(new Object[]{
                        apt.getAppointmentId(),
                        apt.getProviderId(),
                        apt.getDateTime().format(formatter),
                        apt.getConsultationType(),
                        apt.getStatus()
                });
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + ex.getMessage());
        }
    }

    private JPanel createHealthRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Health Records");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JTextArea ehrArea = new JTextArea(20, 60);
        ehrArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ehrArea);

        JButton viewBtn = new JButton("View My EHR");
        viewBtn.addActionListener(e -> {
            if (currentPatient == null) {
                JOptionPane.showMessageDialog(this, "Please login first!");
                return;
            }

            try {
                List<EHR> ehrs = dataManager.loadEHRs();
                for (EHR ehr : ehrs) {
                    if (ehr.getPatientId().equals(currentPatient.getUserId())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("EHR ID: ").append(ehr.getEhrId()).append("\n");
                        sb.append("Patient ID: ").append(ehr.getPatientId()).append("\n");
                        sb.append("Last Updated: ").append(ehr.getLastUpdated()).append("\n\n");

                        sb.append("DIAGNOSES:\n");
                        for (String diag : ehr.getDiagnoses()) {
                            sb.append("- ").append(diag).append("\n");
                        }

                        sb.append("\nTREATMENTS:\n");
                        for (String treat : ehr.getTreatments()) {
                            sb.append("- ").append(treat).append("\n");
                        }

                        ehrArea.setText(sb.toString());
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(viewBtn, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPrescriptionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Prescriptions");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        String[] columns = {"Prescription ID", "Medication", "Dosage", "Frequency", "Issue Date"};
        DefaultTableModel prescTableModel = new DefaultTableModel(columns, 0);
        JTable prescTable = new JTable(prescTableModel);
        JScrollPane scrollPane = new JScrollPane(prescTable);

        JButton viewBtn = new JButton("Load Prescriptions");
        viewBtn.addActionListener(e -> {
            if (currentPatient == null) {
                JOptionPane.showMessageDialog(this, "Please login first!");
                return;
            }

            prescTableModel.setRowCount(0);
            try {
                List<Prescription> prescriptions = dataManager.loadPrescriptions();
                for (Prescription presc : prescriptions) {
                    if (presc.getPatientId().equals(currentPatient.getUserId())) {
                        prescTableModel.addRow(new Object[]{
                                presc.getPrescriptionId(),
                                presc.getMedication(),
                                presc.getDosage(),
                                presc.getFrequency(),
                                presc.getIssueDate()
                        });
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(viewBtn, BorderLayout.SOUTH);

        return panel;
    }
}