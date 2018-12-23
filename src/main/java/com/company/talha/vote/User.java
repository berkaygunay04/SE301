package com.company.talha.vote;

import com.google.firebase.database.PropertyName;

/**
 * The type User.
 */
public class User {
    private String email;
    private   String name;
    private  String surname;

    // private String Adresleri;
    // private String Hediyeleri;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param email   the email
     * @param name    the name
     * @param surname the surname
     */
    public User( String email, String name,String surname) {
        this.email = email;
        this.surname=surname;
        this.name = name;
    }

    /**
     * Gets eposta.
     *
     * @return the eposta
     */
    @PropertyName("email")
    public String getEposta() {
        return email;
    }

    /**
     * Sets eposta.
     *
     * @param eposta the eposta
     */
    @PropertyName("email")
    public void setEposta(String eposta) {
        email = eposta;
    }

    /**
     * Gets ısim.
     *
     * @return the ısim
     */
    @PropertyName("name")
    public String getIsim() {
        return name;
    }

    /**
     * Sets ısim.
     *
     * @param isim the isim
     */
    @PropertyName("name")
    public void setIsim(String isim) {
        name = isim;
    }

    /**
     * Gets soyad.
     *
     * @return the soyad
     */
    @PropertyName("surname")
    public String getSoyad() {
        return surname;
    }

    /**
     * Sets soyad.
     *
     * @param soyad the soyad
     */
    @PropertyName("surname")
    public void setSoyad(String soyad) {
        surname = soyad;
    }


}
