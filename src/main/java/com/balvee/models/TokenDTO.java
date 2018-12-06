package com.balvee.models;


public class TokenDTO {

    // TokenDTO variables

    private String idToken;
    private int user;
    private EmployeeTypes roles;

    // Constructor

    public TokenDTO() {} // TokenDTO

    // Getters and Setters

    public String getIdToken() {
        return idToken;
    } // getIdToken

    public EmployeeTypes getRoles() {
        return roles;
    } // getRoles

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    } // setIdToken

    public int getUser() {
        return user;
    } // getUser

    public void setUser(int user) {
        this.user = user;
    } // setUser

    public void setRoles(EmployeeTypes roles) {
        this.roles = roles;
    } // setRoles

} // TokenDTO class
