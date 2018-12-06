package com.balvee.servlets;

import com.balvee.models.Employee;
import com.balvee.models.EmployeeTypes;
import com.balvee.viewcontrollers.EmployeeViewController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EmployeeServlet extends HttpServlet {

    // EmployeeServlet

    EmployeeViewController evc = new EmployeeViewController();
    static Logger logger = Logger.getLogger(ReimbursementServlet.class);

    // Override

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if(action.equalsIgnoreCase("add")) {
            if(req.getParameter("first_name") == "" ||  req.getParameter("last_name") == "" ||
                    req.getParameter("username") == "" || req.getParameter("email") == "") {
                resp.setStatus(401);
                return;
            }

            Employee e = new Employee();
            try {
                String type = req.getParameter("type");
                if(type.equalsIgnoreCase("1")) {
                    e.setType(EmployeeTypes.EMPLOYEE);
                } else if(type.equalsIgnoreCase("2")){
                    e.setType(EmployeeTypes.MANAGER);
                } else {
                    e.setType(EmployeeTypes.UNKNOWN);
                }
                e.setFirstName(req.getParameter("first_name"));
                e.setLastName(req.getParameter("last_name"));
                e.setUsername(req.getParameter("username"));
                e.setEmail(req.getParameter("email"));
                if(req.getParameter("address") != null) e.setAddress(req.getParameter("address"));
                if(req.getParameter("phone") != null) e.setPhone(req.getParameter("phone"));
                e.setPassword("welcome");
                e.createEmployeeNum();

                evc.addEmployee(e);
                return;
            } catch (NumberFormatException ex) {
                resp.setStatus(400);
                return;
            }
        } else {
            List<Employee> rList = evc.findAll();

            if(!rList.isEmpty()) {
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setStatus(200);

                logger.info("Got all employee's list from database.");
                out.print(new ObjectMapper().writeValueAsString(rList));
                out.flush();
                return;
            }
        }
    } // doGet

} // EmployeeServlet