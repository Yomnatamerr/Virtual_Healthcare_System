import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Main GUI class for VirtualCare 2025 System
 * Provides welcome screen with role selection
 */
public class VirtualCareGUI extends JFrame {
    private DataManager dataManager;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public VirtualCareGUI() {
        dataManager = new DataManager();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("VirtualCare 2025 - Telehealth System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add welcome screen
        mainPanel.add(createWelcomePanel(), "WELCOME");

        add(mainPanel);
        cardLayout.show(mainPanel, "WELCOME");

        setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(900, 100));

        JLabel titleLabel = new JLabel("VirtualCare 2025");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Center content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        JLabel welcomeLabel = new JLabel("Welcome to VirtualCare Telehealth System");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridy = 0;
        centerPanel.add(welcomeLabel, gbc);

        JLabel selectLabel = new JLabel("Please select your role:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        centerPanel.add(selectLabel, gbc);

        // Role buttons
        JButton patientBtn = createStyledButton("Patient", new Color(76, 175, 80));
        patientBtn.addActionListener(e -> showPatientPanel());
        gbc.gridy = 2;
        centerPanel.add(patientBtn, gbc);

        JButton providerBtn = createStyledButton("Healthcare Provider", new Color(33, 150, 243));
        providerBtn.addActionListener(e -> showProviderPanel());
        gbc.gridy = 3;
        centerPanel.add(providerBtn, gbc);

        JButton adminBtn = createStyledButton("Administrator", new Color(156, 39, 176));
        adminBtn.addActionListener(e -> showAdminPanel());
        gbc.gridy = 4;
        centerPanel.add(adminBtn, gbc);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 248, 255));
        JLabel footerLabel = new JLabel("© 2025 VirtualCare - Quality Healthcare at Your Fingertips");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerPanel.add(footerLabel);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void showPatientPanel() {
        // Check if panel already exists
        boolean exists = false;
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof PatientPanel) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            mainPanel.add(new PatientPanel(dataManager, this), "PATIENT");
        }
        cardLayout.show(mainPanel, "PATIENT");
    }

    private void showProviderPanel() {
        boolean exists = false;
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof ProviderPanel) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            mainPanel.add(new ProviderPanel(dataManager, this), "PROVIDER");
        }
        cardLayout.show(mainPanel, "PROVIDER");
    }

    private void showAdminPanel() {
        boolean exists = false;
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof AdminPanel) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            mainPanel.add(new AdminPanel(dataManager, this), "ADMIN");
        }
        cardLayout.show(mainPanel, "ADMIN");
    }

    public void showWelcome() {
        cardLayout.show(mainPanel, "WELCOME");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VirtualCareGUI();
        });
    }
}