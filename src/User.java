import java.io.Serializable;

/**
 * Abstract base class for all system users
 * Implements inheritance hierarchy as per UML diagram
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String userId;
    protected String name;
    protected String email;
    protected String phone;
    protected String password;

    public User() {
    }

    public User(String userId, String name, String email, String phone, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getRole();

    @Override
    public String toString() {
        return "User ID: " + userId + ", Name: " + name + ", Email: " + email;
    }
}