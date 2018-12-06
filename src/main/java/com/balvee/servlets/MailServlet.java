package com.balvee.servlets;

import com.balvee.models.Employee;
import com.balvee.models.Mail;
import com.balvee.viewcontrollers.EmployeeViewController;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MailServlet extends HttpServlet {

    // MailServlet Variables

    EmployeeViewController evc = new EmployeeViewController();

    // Override

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Employee e = new Employee();
            e.setEmail(req.getParameter("email"));
            e.createPassword();
            evc.forgotPassword(e);
            Mail.sendPassword(e);
        } catch (Exception e) {}
    } // doGet

} // MailServlet