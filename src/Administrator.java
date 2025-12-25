/**
 * Administrator class extending User
 * Manages system configuration and provider registration
 */
public class Administrator extends User {
    private static final long serialVersionUID = 1L;
    private static int adminCounter = 3000;

    private String department;

    public Administrator() {
        super();
    }

    public Administrator(String name, String email, String phone, String password, String department) {
        super(generateAdminId(), name, email, phone, password);
        this.department = department;
    }

    private static String generateAdminId() {
        return "A" + (adminCounter++);
    }

    public static void setAdminCounter(int counter) {
        adminCounter = counter;
    }

    @Override
    public String getRole() {
        return "ADMINISTRATOR";
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: Administrator, Department: " + department;
    }
}