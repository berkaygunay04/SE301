package com.company.talha.vote;

import com.google.firebase.database.PropertyName;

public class User {
    private String email;
    private   String name;
    private  String surname;

    // private String Adresleri;
    // private String Hediyeleri;

    public User() {
    }

    public User( String email, String name,String surname) {
        this.email = email;
        this.surname=surname;
        this.name = name;
    }

    @PropertyName("email")
    public String getEposta() {
        return email;
    }
    @PropertyName("email")
    public void setEposta(String eposta) {
        email = eposta;
    }

    @PropertyName("name")
    public String getIsim() {
        return name;
    }

    @PropertyName("name")
    public void setIsim(String isim) {
        name = isim;
    }

    @PropertyName("surname")
    public String getSoyad() {
        return surname;
    }

    @PropertyName("surname")
    public void setSoyad(String soyad) {
        surname = soyad;
    }

}
