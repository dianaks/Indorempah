package com.blibli.future.Model;

import javax.persistence.*;

/**
 * Created by Nita on 16/09/2016.
 */
@Entity
@Table(name="indorempah_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled = true;

    private String picture;
    private String companyName;
    private String companyAddress;
    private String phoneNumber;
    private String bankAccountNumber;

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

    public void setUserRole(com.blibli.future.Model.UserRole userRole) {
        UserRole = userRole;
    }

    public void setUserRole(String role) {
        this.UserRole = UserRole;
    }

    public boolean getEnabled() {
        return enabled;

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled ;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
