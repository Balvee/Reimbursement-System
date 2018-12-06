package com.balvee.viewcontrollers;

import com.balvee.daoimplements.ReimbursementDaoImpl;
import com.balvee.models.Reimbursement;
import org.apache.log4j.Logger;

import java.util.List;

public class ReimbursementViewController {

    // ReimbursementViewController Variables

    ReimbursementDaoImpl rd = new ReimbursementDaoImpl();
    List<Reimbursement> rList;
    static Logger logger = Logger.getLogger(ReimbursementViewController.class);

    // Actions

    public List<Reimbursement> findOne(int employee_num, int status) {
        try {
            rList = rd.findOne(employee_num, status);
            return rList;
        } catch (Exception e) {}
        return null;
    } // findOne

    public List<Reimbursement> findAll(int status, int user) {
        try {
            rList = rd.findAll(status, user);
            logger.info("Found All Reimbursements for all users.");
            return rList;
        } catch (Exception e) {}

        logger.error("Did not find any reimbursements for all users.");
        return null;
    } // findAll

    public void addReimbursement (Reimbursement r) { rd.addReimbursement(r); } // addReimbursement

    public void updateReimbursement (Reimbursement r) { rd.updateReimbursement(r); } // updateReimbursement


} // UserController class