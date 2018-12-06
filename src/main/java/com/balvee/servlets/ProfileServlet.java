package com.balvee.servlets;

import com.balvee.models.Employee;
import com.balvee.models.Reimbursement;
import com.balvee.viewcontrollers.EmployeeViewController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ProfileServlet extends HttpServlet {

    // ProfileServlet variables

    EmployeeViewController evc = new EmployeeViewController();
    static Logger logger = Logger.getLogger(ReimbursementServlet.class);

    // Overrides

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        switch (action) {
            case "GET":
                int num = Integer.parseInt(req.getParameter("employeeNum"));
                Employee employee = evc.findOne(num);

                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setStatus(200);

                logger.info("Got all employee's list from database.");
                out.print(new ObjectMapper().writeValueAsString(employee));
                out.flush();
                return;

            case "update":
                Employee e = new Employee();
                try {
                    String first_name = req.getParameter("first_name");
                    String last_name = req.getParameter("last_name");
                    String phone = req.getParameter("phone");
                    String employee_num = req.getParameter("user");
                    String address = req.getParameter("address");
                    String email = req.getParameter("email");
                    String password = req.getParameter("password");

                    if(first_name != null) e.setFirstName(first_name);
                    if(last_name != null) e.setLastName(last_name);
                    if(address != null) e.setAddress(address);
                    if(phone != null) e.setPhone(phone);
                    if(email != null) e.setEmail(email);
                    if(password != "") e.setPassword(password);
                    e.setEmployeeNum(Integer.parseInt(employee_num));
                    evc.updateUser(e);
                    return;
                } catch (NumberFormatException ex) {
                    resp.setStatus(400);
                    return;
                }
            default:
        }
    } // doGet

}// ProfileServlet