package com.balvee.models;

import java.util.Random;

public class Employee {

    // Employee Variables

    EmployeeTypes type;
    int employeeNum;
    String firstName, lastName;
    String username, email, phone, address, password;

    // Constructors

    public Employee () {} // Default User constructor

    // Getter and Setters

    public EmployeeTypes getType() {
        return type;
    } // getType

    public void setType(EmployeeTypes type) {
        this.type = type;
    } // setType

    public int getEmployeeNum() {
        return employeeNum;
    } // getEmployeeNum

    public void setEmployeeNum(int employeeNum) {
        this.employeeNum = employeeNum;
    } // setEmployeeNum

    public String getFirstName() {
        return firstName;
    } // getFirstName

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    } // setFirstName

    public String getLastName() {
        return lastName;
    } // getLastName

    public void setLastName(String lastName) {
        this.lastName = lastName;
    } // setLastName

    public String getUsername() {
        return username;
    } // getUsername

    public void setUsername(String username) {
        this.username = username;
    } // setUsername

    public String getPassword() {
        return password;
    } // getPassword

    public void setPassword(String password) {
        this.password = password;
    } // setPassword

    public String getEmail() {
        return email;
    } // getEmail

    public void setEmail(String email) {
        this.email = email;
    } // setEmail

    public String getPhone() {
        return phone;
    } // getPhone

    public void setPhone(String phone) {
        this.phone = phone;
    } // setPhone

    public String getAddress() {
        return address;
    } // getAddress

    public void setAddress(String address) {
        this.address = address;
    } // setAddress



    // Actions

    public void createEmployeeNum(){
        Random r = new Random();
        this.employeeNum = r.nextInt(9999999);
        while(employeeNum < 999999){
            this.employeeNum = r.nextInt();
        }
    } // createEmployeeNum

    public String createPassword() {
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";

        String values = Capital_chars + Small_chars + numbers + symbols;

        // Using random method
        Random rndm_method = new Random();

        char[] password = new char[8];

        for (int i = 0; i < 8; i++) password[i] = values.charAt(rndm_method.nextInt(values.length()));

        this.password = password.toString();
        return this.password;
    } // createPassword

} // Employee class