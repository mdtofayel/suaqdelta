package com.happyride.eservice.entity.model;

import javax.persistence.*;

@Entity
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean mailVerified;

    private boolean phoneNumberVerified;

    private boolean addressVerified;

    private boolean bankAccountVerified;

    @OneToOne
    private Users users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

    public boolean isPhoneNumberVerified() {
        return phoneNumberVerified;
    }

    public void setPhoneNumberVerified(boolean phoneNumberVerified) {
        this.phoneNumberVerified = phoneNumberVerified;
    }

    public boolean isAddressVerified() {
        return addressVerified;
    }

    public void setAddressVerified(boolean addressVerified) {
        this.addressVerified = addressVerified;
    }

    public boolean isBankAccountVerified() {
        return bankAccountVerified;
    }

    public void setBankAccountVerified(boolean bankAccountVerified) {
        this.bankAccountVerified = bankAccountVerified;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
