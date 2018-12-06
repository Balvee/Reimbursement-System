package com.balvee.servlets;

import com.balvee.models.Employee;
import com.balvee.models.TokenDTO;
import com.balvee.viewcontrollers.EmployeeViewController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    // AuthServlet Variables

    EmployeeViewController evc = new EmployeeViewController();
    static Logger logger = Logger.getLogger(AuthServlet.class);

    // Override

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //get username and password
        Employee e = new ObjectMapper().readValue(req.getInputStream(), Employee.class);

        if(e.getUsername() == null || e.getPassword() == null) {
            resp.setStatus(400);
            logger.error("User did not type in username or password");
            return;
        }

        //send authService
        e = evc.authenticate(e.getUsername(), e.getPassword());
        //if authenticated set session
        if(e != null) {
            TokenDTO token = evc.getToken(e, 2592000);
            resp.getWriter().write(new ObjectMapper().writeValueAsString(token));
            resp.setStatus(200);
            return;
        } else {
            //if !autheticated send 401
            logger.error("Token not created");
            resp.setStatus(401);
            return;
        }
    } // doPost

}// AuthServlet
