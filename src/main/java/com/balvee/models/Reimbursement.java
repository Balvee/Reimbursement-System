package com.balvee.models;

public class Reimbursement {

    // Reimbursement Variables

    private int id;
    private ReimbursementStatus type;
    private String employeeFirstName, employeeLastName;
    private String managerFirstName, managerLastName;
    private int employeeNum, managerNum;
    private String date_time;
    private float amount;
    private String note;


    // Constructors

    public Reimbursement () {} // Default Reimbursement constructor

    // Getter and Setters

    public int getId() {
        return id;
    } // getId

    public void setId(int id) {
        this.id = id;
    } // setId

    public ReimbursementStatus getType() {
        return type;
    } // getType

    public void setType(ReimbursementStatus type) {
        this.type = type;
    } // setType

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    } // setEmployeeFirstName

    public String getNote() {
        return note;
    } // getNote

    public void setNote(String note) {
        this.note = note;
    } // setNote

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    } // setEmployeeLastName

    public void setManagerFirstName(String managerFirstName) {
        this.managerFirstName = managerFirstName;
    } // setManagerFirstName

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    } // setManagerLastName

    public String getDate_time() {
        return date_time;
    } // getDate_time

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    } // setDate_time

    public float getAmount() {
        return amount;
    } // getAmount

    public void setAmount(float amount) {
        this.amount = amount;
    } // setAmount

    public int getEmployeeNum() {
        return employeeNum;
    } // getEmployeeNum

    public void setEmployeeNum(int employeeNum) {
        this.employeeNum = employeeNum;
    } // setEmployeeNum

    public int getManagerNum() {
        return managerNum;
    } // getManagerNum

    public void setManagerNum(int managerNum) {
        this.managerNum = managerNum;
    } // setManagerNum

    public String getEmployeeFirstName() {
        return employeeFirstName;
    } // getEmployeeFirstName

    public String getEmployeeLastName() {
        return employeeLastName;
    } // getEmployeeLastName

    public String getManagerFirstName() {
        return managerFirstName;
    } // getManagerFirstName

    public String getManagerLastName() {
        return managerLastName;
    } // getManagerLastName

    // Actions

    @Override
    public String toString() {
        return "Reimbursement{" +
                "type=" + type +
                ", employeeFirstName='" + employeeFirstName + '\'' +
                ", employeeLastName='" + employeeLastName + '\'' +
                ", managerFirstName='" + managerFirstName + '\'' +
                ", managerLastName='" + managerLastName + '\'' +
                ", date_time='" + date_time + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    } // toString

} // User class