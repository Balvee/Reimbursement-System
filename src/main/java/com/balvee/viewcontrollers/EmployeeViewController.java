package com.balvee.viewcontrollers;


import com.balvee.daoimplements.EmployeeDaoImpl;
import com.balvee.models.Employee;
import com.balvee.models.TokenDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class EmployeeViewController {

    // EmployeeViewController Variables

    EmployeeDaoImpl ed = new EmployeeDaoImpl();
    static Logger logger = Logger.getLogger(EmployeeViewController.class);

    // Actions

    public Employee findOne(int employeeNum) {
        EmployeeDaoImpl ed = new EmployeeDaoImpl();
        Employee e;
        try {
            e = ed.findOne(employeeNum);
            logger.info("Found All Employees.");
            return e;
        } catch (Exception ex) {

        }

        logger.error("Did not find any employees.");
        return null;
    } // findOne

    public List<Employee> findAll() {
        EmployeeDaoImpl ed = new EmployeeDaoImpl();
        List<Employee> rList;
        try {
            rList = ed.findAll();
            logger.info("Found All Employees.");
            return rList;
        } catch (Exception e) {

        }

        logger.error("Did not find any employees.");
        return null;
    } // findAll

    public Employee authenticate(String username, String password) {

        try {
            Employee e = ed.findOne(username, password);
            return e;
        } catch (Exception e) {

        }

        return null;
    } // authenticate

    public void addEmployee(Employee e){
        ed.addEmployee(e);
    } // addEmployee

    public void updateUser (Employee e) { ed.updateUser(e); } // updateUser

    public void forgotPassword(Employee e) {
        ed.forgotPassword(e);
    } // forgotPassword

    public TokenDTO getToken(Employee e, long ttl) throws UnsupportedEncodingException {
        TokenDTO token = new TokenDTO();
        Date now = new Date();
        Date future = Date.from(Instant.ofEpochMilli(now.getTime() + ttl));

        String jwt = Jwts.builder()
                .setSubject(e.getUsername())
                .setIssuedAt(now)
                .setExpiration(future)
                .claim("user", e.getEmployeeNum())
                .claim("roles", e.getType())
                .signWith(SignatureAlgorithm.HS256, "mySecret".getBytes("UTF-8"))
                .compact();

        token.setIdToken(jwt);
        token.setUser(e.getEmployeeNum());
        token.setRoles(e.getType());

        logger.info("Token created.");
        return token;
    } // getToken

} // EmployeeViewController