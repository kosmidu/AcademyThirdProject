package com.afse.academy.model;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {

    private static final long serialVersionUID = -4730744828078754202L;

    public Customer() {}

    public Customer(Long id, String fName, String lName, String user, Date birthDate,
                    String country, String city, String street, String zipCode,
                    String phone, String email, boolean acceptTerms) {
        setId(id);
        setFirstName(fName);
        setLastName(lName);
        setUsername(user);
        setBirthDate(birthDate);
        setCountry(country);
        setCity(city);
        setStreet(street);
        setZipCode(zipCode);
        setPhone(phone);
        setEmail(email);
        setAcceptTerms(acceptTerms);
    }

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private Date birthDate;

    private String country;

    private String city;

    private String street;

    private String zipCode;

    private String phone;

    private String email;

    private boolean acceptTerms;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAcceptTerms() {
        return acceptTerms;
    }

    public void setAcceptTerms(boolean acceptTerms) {
        this.acceptTerms = acceptTerms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}