package com.blibli.future.Model;

import javax.persistence.*;

/**
 * Created by Nita on 16/09/2016.
 */
@Entity
@Table(name="indorempah_user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled = true;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserRole UserRole;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public UserRole getUserRole() {
        return UserRole;

    }
    public void setUserRole(String role) {
        this.UserRole = UserRole;
    }
    public boolean getEnabled() {
        return enabled;

    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled ;
    }
}
