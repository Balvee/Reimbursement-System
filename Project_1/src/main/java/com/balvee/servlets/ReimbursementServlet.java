package com.balvee.servlets;

import com.balvee.models.Reimbursement;
import com.balvee.models.ReimbursementStatus;
import com.balvee.viewcontrollers.ReimbursementViewController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReimbursementServlet extends HttpServlet {

    // ReimbursementServlet

    ReimbursementViewController rvc = new ReimbursementViewController();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    static Logger logger = Logger.getLogger(ReimbursementServlet.class);

    // Override

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Reimbursement r;
        String type = req.getParameter("employeeType");
        String action = req.getParameter("action");

        switch (action) {
            case "GET":
                if(type.equalsIgnoreCase("MANAGER")) {
                    int status = Integer.parseInt(req.getParameter("status"));
                    int user = Integer.parseInt(req.getParameter("user"));
                    List<Reimbursement> rList = rvc.findAll(status, user);
                    if(rList != null && rList.size() != 0) {
                        PrintWriter out = resp.getWriter();
                        resp.setContentType("application/json");
                        resp.setStatus(200);

                        logger.info("Created Reimbursement List from all users.");
                        out.print(new ObjectMapper().writeValueAsString(rList));
                        out.flush();
                        return;
                    }
                    return;
                } else {
                    int num = Integer.parseInt(req.getParameter("employeeNum"));
                    int status = Integer.parseInt(req.getParameter("status"));
                    List<Reimbursement> rList = rvc.findOne(num, status);
                    if(rList != null && rList.size() != 0) {
                        PrintWriter out = resp.getWriter();
                        resp.setContentType("application/json");
                        resp.setStatus(200);

                        logger.info("Created Reimbursement List for " + num + " user.");
                        out.print(new ObjectMapper().writeValueAsString(rList));
                        out.flush();
                        return;
                    }
                    return;
                }
            case "add":
                r = new Reimbursement();
                try {

                    Float amt = Float.parseFloat(req.getParameter("amount"));
                    r.setAmount(amt);
                    r.setNote(req.getParameter("note"));
                    int num = Integer.parseInt(req.getParameter("user"));
                    r.setEmployeeNum(num);
                    r.setDate_time(dtf.format(now));
                    rvc.addReimbursement(r);
                    return;
                } catch (NumberFormatException e) {
                    resp.setStatus(400);
                    return;
                }
            case "update":
                r = new Reimbursement();
                try {
                    int num = Integer.parseInt(req.getParameter("manager_num"));
                    r.setManagerNum(num);
                    if(req.getParameter("type").equalsIgnoreCase("1")) r.setType(ReimbursementStatus.APPROVED);
                    else if (req.getParameter("type").equalsIgnoreCase("2")) r.setType(ReimbursementStatus.REJECTED);
                    else r.setType(ReimbursementStatus.PENDING);
                    int id = Integer.parseInt(req.getParameter("id"));
                    r.setId(id);
                    rvc.updateReimbursement(r);
                    return;
                } catch (NumberFormatException e) {
                    resp.setStatus(400);
                    return;
                }
            default:

        }
    } // doGet

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

} // ReimbursementServlet clas
