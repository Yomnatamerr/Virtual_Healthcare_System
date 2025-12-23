// VirtualHealthcareGUI.java - Main GUI Application
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirtualHealthcareGUI extends JFrame {
    private List<Patient> patients;
    private List<Provider> providers;
    private List<Appointment> appointments;
    private List<Bill> bills;
    private List<Feedback> feedbackList;

    public VirtualHealthcareGUI() {
        setTitle("Virtual Healthcare System 2025");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadAllData();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Patients", createPatientPanel());
        tabbedPane.addTab("Providers", createProviderPanel());
        tabbedPane.addTab("Appointments", createAppointmentPanel());
        tabbedPane.addTab("Billing", createBillingPanel());
        tabbedPane.addTab("Feedback", createFeedbackPanel());

        add(tabbedPane);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveAllData();
            }
        });
    }

    private void loadAllData() {
        try {
            patients = DataManager.loadPatients();
            providers = DataManager.loadProviders();
            appointments = DataManager.loadAppointments();
            bills = DataManager.loadBills();
            feedbackList = DataManager.loadFeedback();
        } catch (Exception e) {
            patients = new ArrayList<>();
            providers = new ArrayList<>();
            appointments = new ArrayList<>();
            bills = new ArrayList<>();
            feedbackList = new ArrayList<>();
            addSampleData();
        }
    }

    private void saveAllData() {
        try {
            DataManager.savePatients(patients);
            DataManager.saveProviders(providers);
            DataManager.saveAppointments(appointments);
            DataManager.saveBills(bills);
            DataManager.saveFeedback(feedbackList);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage());
        }
    }

    private void addSampleData() {
        patients.add(new Patient("U001", "John Doe", "john@email.com", "pass",
                "555-1234", "P001", "Diabetes", "Jane-555-5678"));
        patients.add(new Patient("U002", "Sarah Smith", "sarah@email.com", "pass",
                "555-2345", "P002", "Asthma", "Mike-555-6789"));

        providers.add(new Provider("U101", "Dr. Smith", "drsmith@email.com", "doc",
                "555-3456", "PR001", "Cardiology", "Mon-Fri 9-5"));
        providers.add(new Provider("U102", "Nurse Lucy", "lucy@email.com", "nurse",
                "555-4567", "PR002", "General", "Mon-Sat 8-4"));

        appointments.add(new Appointment("A001", "P001", "PR001", "2025-12-24 10:00", "Video"));
        appointments.add(new Appointment("A002", "P002", "PR002", "2025-12-25 14:00", "Video"));

        bills.add(new Bill("B001", "P001", "A001", 150.00));
        bills.add(new Bill("B002", "P002", "A002", 100.00));
    }

    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Patient ID", "Name", "Email", "Phone", "History"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        refreshPatients(model);

        JPanel btnPanel = new JPanel();
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");
        JButton ref = new JButton("Refresh");

        add.addActionListener(e -> addPatient(model));
        edit.addActionListener(e -> editPatient(table, model));
        del.addActionListener(e -> deletePatient(table, model));
        ref.addActionListener(e -> refreshPatients(model));

        btnPanel.add(add); btnPanel.add(edit); btnPanel.add(del); btnPanel.add(ref);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshPatients(DefaultTableModel m) {
        m.setRowCount(0);
        for (Patient p : patients)
            m.addRow(new Object[]{p.getPatientId(), p.getName(), p.getEmail(),
                    p.getPhoneNumber(), p.getMedicalHistory()});
    }

    private void addPatient(DefaultTableModel m) {
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JTextField hist = new JTextField();
        JTextField emerg = new JTextField();

        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        p.add(new JLabel("Name:")); p.add(name);
        p.add(new JLabel("Email:")); p.add(email);
        p.add(new JLabel("Phone:")); p.add(phone);
        p.add(new JLabel("History:")); p.add(hist);
        p.add(new JLabel("Emergency:")); p.add(emerg);

        if (JOptionPane.showConfirmDialog(this, p, "Add Patient",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String pid = "P" + String.format("%03d", patients.size() + 1);
            String uid = "U" + String.format("%03d", patients.size() + 100);
            patients.add(new Patient(uid, name.getText(), email.getText(), "default",
                    phone.getText(), pid, hist.getText(), emerg.getText()));
            refreshPatients(m);
            saveAllData();
            JOptionPane.showMessageDialog(this, "Added! ID: " + pid);
        }
    }

    private void editPatient(JTable t, DefaultTableModel m) {
        int row = t.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient");
            return;
        }

        String id = (String) m.getValueAt(row, 0);
        Patient pt = DataManager.searchPatientById(patients, id);

        if (pt != null) {
            JTextField name = new JTextField(pt.getName());
            JTextField email = new JTextField(pt.getEmail());
            JTextField phone = new JTextField(pt.getPhoneNumber());
            JTextField hist = new JTextField(pt.getMedicalHistory());

            JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
            p.add(new JLabel("Name:")); p.add(name);
            p.add(new JLabel("Email:")); p.add(email);
            p.add(new JLabel("Phone:")); p.add(phone);
            p.add(new JLabel("History:")); p.add(hist);

            if (JOptionPane.showConfirmDialog(this, p, "Edit Patient",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                pt.setName(name.getText());
                pt.setEmail(email.getText());
                pt.setPhoneNumber(phone.getText());
                pt.setMedicalHistory(hist.getText());
                refreshPatients(m);
                saveAllData();
                JOptionPane.showMessageDialog(this, "Updated!");
            }
        }
    }

    private void deletePatient(JTable t, DefaultTableModel m) {
        int row = t.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient");
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Delete this patient?",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String id = (String) m.getValueAt(row, 0);
            patients.removeIf(p -> p.getPatientId().equals(id));
            refreshPatients(m);
            saveAllData();
            JOptionPane.showMessageDialog(this, "Deleted!");
        }
    }

// Continuation of VirtualHealthcareGUI.java - Add these methods to Part 1

    private JPanel createProviderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Provider ID", "Name", "Email", "Specialty", "Availability"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        refreshProviders(model);

        JPanel btnPanel = new JPanel();
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> addProvider(model));
        edit.addActionListener(e -> editProvider(table, model));
        del.addActionListener(e -> deleteProvider(table, model));

        btnPanel.add(add); btnPanel.add(edit); btnPanel.add(del);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshProviders(DefaultTableModel m) {
        m.setRowCount(0);
        for (Provider p : providers)
            m.addRow(new Object[]{p.getProviderId(), p.getName(), p.getEmail(),
                    p.getSpecialty(), p.getAvailability()});
    }

    private void addProvider(DefaultTableModel m) {
        JTextField name = new JTextField(), email = new JTextField();
        JTextField phone = new JTextField(), spec = new JTextField();
        JTextField avail = new JTextField();

        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        p.add(new JLabel("Name:")); p.add(name);
        p.add(new JLabel("Email:")); p.add(email);
        p.add(new JLabel("Phone:")); p.add(phone);
        p.add(new JLabel("Specialty:")); p.add(spec);
        p.add(new JLabel("Availability:")); p.add(avail);

        if (JOptionPane.showConfirmDialog(this, p, "Add Provider",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String pid = "PR" + String.format("%03d", providers.size() + 1);
            String uid = "U" + String.format("%03d", providers.size() + 200);
            providers.add(new Provider(uid, name.getText(), email.getText(), "default",
                    phone.getText(), pid, spec.getText(), avail.getText()));
            refreshProviders(m);
            saveAllData();
            JOptionPane.showMessageDialog(this, "Added! ID: " + pid);
        }
    }

    private void editProvider(JTable t, DefaultTableModel m) {
        int row = t.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select provider"); return; }

        Provider pv = DataManager.searchProviderById(providers, (String) m.getValueAt(row, 0));
        if (pv != null) {
            JTextField name = new JTextField(pv.getName());
            JTextField email = new JTextField(pv.getEmail());
            JTextField spec = new JTextField(pv.getSpecialty());
            JTextField avail = new JTextField(pv.getAvailability());

            JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
            p.add(new JLabel("Name:")); p.add(name);
            p.add(new JLabel("Email:")); p.add(email);
            p.add(new JLabel("Specialty:")); p.add(spec);
            p.add(new JLabel("Availability:")); p.add(avail);

            if (JOptionPane.showConfirmDialog(this, p, "Edit",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                pv.setName(name.getText()); pv.setEmail(email.getText());
                pv.setSpecialty(spec.getText()); pv.setAvailability(avail.getText());
                refreshProviders(m); saveAllData();
                JOptionPane.showMessageDialog(this, "Updated!");
            }
        }
    }

    private void deleteProvider(JTable t, DefaultTableModel m) {
        int row = t.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select provider"); return; }
        if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            providers.removeIf(p -> p.getProviderId().equals(m.getValueAt(row, 0)));
            refreshProviders(m); saveAllData();
        }
    }

    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Apt ID", "Patient", "Provider", "Date/Time", "Type", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        refreshAppointments(model);

        JPanel btnPanel = new JPanel();
        JButton add = new JButton("Book");
        JButton status = new JButton("Update Status");
        JButton del = new JButton("Cancel");

        add.addActionListener(e -> addAppointment(model));
        status.addActionListener(e -> updateStatus(table, model));
        del.addActionListener(e -> deleteAppointment(table, model));

        btnPanel.add(add); btnPanel.add(status); btnPanel.add(del);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshAppointments(DefaultTableModel m) {
        m.setRowCount(0);
        for (Appointment a : appointments)
            m.addRow(new Object[]{a.getAppointmentId(), a.getPatientId(),
                    a.getProviderId(), a.getDateTime(), a.getConsultationType(), a.getStatus()});
    }

    private void addAppointment(DefaultTableModel m) {
        JComboBox<String> patBox = new JComboBox<>();
        for (Patient p : patients) patBox.addItem(p.getPatientId() + "-" + p.getName());

        JComboBox<String> provBox = new JComboBox<>();
        for (Provider p : providers) provBox.addItem(p.getProviderId() + "-" + p.getName());

        JTextField dateTime = new JTextField("2025-12-24 10:00");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Video", "Phone", "In-person"});

        JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
        p.add(new JLabel("Patient:")); p.add(patBox);
        p.add(new JLabel("Provider:")); p.add(provBox);
        p.add(new JLabel("Date/Time:")); p.add(dateTime);
        p.add(new JLabel("Type:")); p.add(typeBox);

        if (JOptionPane.showConfirmDialog(this, p, "Book Appointment",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String aid = "A" + String.format("%03d", appointments.size() + 1);
            String pid = ((String) patBox.getSelectedItem()).split("-")[0];
            String prid = ((String) provBox.getSelectedItem()).split("-")[0];
            appointments.add(new Appointment(aid, pid, prid, dateTime.getText(),
                    (String) typeBox.getSelectedItem()));
            refreshAppointments(m); saveAllData();
            JOptionPane.showMessageDialog(this, "Booked! ID: " + aid);
        }
    }

    private void updateStatus(JTable t, DefaultTableModel m) {
        int row = t.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select appointment"); return; }

        Appointment apt = DataManager.searchAppointmentById(appointments,
                (String) m.getValueAt(row, 0));
        if (apt != null) {
            String[] statuses = {"Scheduled", "Completed", "Cancelled", "Rescheduled"};
            String newStatus = (String) JOptionPane.showInputDialog(this, "Status:",
                    "Update", JOptionPane.QUESTION_MESSAGE, null, statuses, apt.getStatus());
            if (newStatus != null) {
                apt.setStatus(newStatus);
                refreshAppointments(m); saveAllData();
            }
        }
    }

    private void deleteAppointment(JTable t, DefaultTableModel m) {
        int row = t.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select appointment"); return; }
        if (JOptionPane.showConfirmDialog(this, "Cancel?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            appointments.removeIf(a -> a.getAppointmentId().equals(m.getValueAt(row, 0)));
            refreshAppointments(m); saveAllData();
        }
    }

    private JPanel createBillingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Bill ID", "Patient", "Appointment", "Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        refreshBills(model);

        JPanel btnPanel = new JPanel();
        JButton add = new JButton("Create Bill");
        JButton pay = new JButton("Mark Paid");

        add.addActionListener(e -> addBill(model));
        pay.addActionListener(e -> markPaid(table, model));

        btnPanel.add(add); btnPanel.add(pay);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshBills(DefaultTableModel m) {
        m.setRowCount(0);
        for (Bill b : bills)
            m.addRow(new Object[]{b.getBillId(), b.getPatientId(), b.getAppointmentId(),
                    String.format("$%.2f", b.getAmount()), b.getStatus()});
    }

    private void addBill(DefaultTableModel m) {
        JComboBox<String> patBox = new JComboBox<>();
        for (Patient p : patients) patBox.addItem(p.getPatientId() + "-" + p.getName());
        JComboBox<String> aptBox = new JComboBox<>();
        for (Appointment a : appointments) aptBox.addItem(a.getAppointmentId());
        JTextField amt = new JTextField("100.00");

        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        p.add(new JLabel("Patient:")); p.add(patBox);
        p.add(new JLabel("Appointment:")); p.add(aptBox);
        p.add(new JLabel("Amount:")); p.add(amt);

        if (JOptionPane.showConfirmDialog(this, p, "Create Bill",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String bid = "B" + String.format("%03d", bills.size() + 1);
                bills.add(new Bill(bid, ((String) patBox.getSelectedItem()).split("-")[0],
                        (String) aptBox.getSelectedItem(), Double.parseDouble(amt.getText())));
                refreshBills(m); saveAllData();
                JOptionPane.showMessageDialog(this, "Bill created! ID: " + bid);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount");
            }
        }
    }

    private void markPaid(JTable t, DefaultTableModel m) {
        int row = t.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select bill"); return; }

        for (Bill b : bills) {
            if (b.getBillId().equals(m.getValueAt(row, 0))) {
                b.setStatus("Paid");
                refreshBills(m); saveAllData();
                JOptionPane.showMessageDialog(this, "Marked as paid!");
                break;
            }
        }
    }

    private JPanel createFeedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Patient", "Provider", "Rating", "Comments"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        refreshFeedback(model);

        JPanel btnPanel = new JPanel();
        JButton add = new JButton("Add Feedback");
        add.addActionListener(e -> addFeedback(model));
        btnPanel.add(add);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshFeedback(DefaultTableModel m) {
        m.setRowCount(0);
        for (Feedback f : feedbackList)
            m.addRow(new Object[]{f.getFeedbackId(), f.getPatientId(),
                    f.getProviderId(), f.getRating() + "/5", f.getComments()});
    }

    private void addFeedback(DefaultTableModel m) {
        JComboBox<String> patBox = new JComboBox<>();
        for (Patient p : patients) patBox.addItem(p.getPatientId() + "-" + p.getName());
        JComboBox<String> provBox = new JComboBox<>();
        for (Provider p : providers) provBox.addItem(p.getProviderId() + "-" + p.getName());
        JComboBox<Integer> rating = new JComboBox<>(new Integer[]{1,2,3,4,5});
        JTextField comments = new JTextField();

        JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
        p.add(new JLabel("Patient:")); p.add(patBox);
        p.add(new JLabel("Provider:")); p.add(provBox);
        p.add(new JLabel("Rating:")); p.add(rating);
        p.add(new JLabel("Comments:")); p.add(comments);

        if (JOptionPane.showConfirmDialog(this, p, "Add Feedback",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String fid = "F" + String.format("%03d", feedbackList.size() + 1);
            feedbackList.add(new Feedback(fid,
                    ((String) patBox.getSelectedItem()).split("-")[0],
                    ((String) provBox.getSelectedItem()).split("-")[0],
                    (Integer) rating.getSelectedItem(), comments.getText()));
            refreshFeedback(m); saveAllData();
            JOptionPane.showMessageDialog(this, "Feedback submitted!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VirtualHealthcareGUI().setVisible(true);
        });
    }
}
