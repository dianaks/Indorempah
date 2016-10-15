package com.blibli.future.Model;
import com.blibli.future.Model.UserType;

import javax.persistence.*;

/**
 * Created by Nita on 16/09/2016.
 */
@Entity
@Table(name="Indorempah_user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserType type;

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
    public UserType getUserType() {
        return type;
    }
}
