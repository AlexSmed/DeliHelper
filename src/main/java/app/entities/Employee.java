package app.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;
import app.security.ISecurityUser;
import org.mindrot.jbcrypt.BCrypt;

@Entity
public class Employee implements ISecurityUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int id;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password", length = 100)
    private String  password;

    @Column(name = "store_id")
    private int store_id;

    @Column(name = "is_admin")
    private boolean is_admin;

    public Employee(){}

    @Override
    public Set<String> getRolesAsStrings() {
        Set<String> roles = new HashSet<>();

        roles.add("USER");

        if (is_admin) {
            roles.add("ADMIN");
        }

        return roles;
    }

    @Override
    public boolean verifyPassword(String password) {
        return password != null
                && this.password != null
                && BCrypt.checkpw(password, this.password);
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(
                password,
                BCrypt.gensalt(12)
        );
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}
