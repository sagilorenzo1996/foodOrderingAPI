package com.finalYearProject.foodOrderingAPI.domain;

import java.io.Serializable;

public class Login implements Serializable {

    private String telephone;
    private String password;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}