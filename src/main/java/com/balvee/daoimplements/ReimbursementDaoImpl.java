package com.balvee.daoimplements;

import com.balvee.models.Employee;
import com.balvee.models.Mail;
import com.balvee.models.Reimbursement;
import com.balvee.models.ReimbursementStatus;
import com.balvee.util.DBConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoImpl {

    static Logger logger = Logger.getLogger(ReimbursementDaoImpl.class);

    // Database Actions

    public List<Reimbursement> findOne(int employee_num, int status) {
    Connection c = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Reimbursement r;
    List<Reimbursement> rList = new ArrayList<>();

        try {
        c = DBConnectionUtil.newConnection();

        //turn of autocommit
        c.setAutoCommit(false);
        if (status != 0) {
            String sql = "Select distinct status, date_time, amount,(" +
                    "Select first_name from employee where employee_num = manager_num), (" +
                    "Select last_name from employee where employee_num = manager_num), note " +
                    "from employee, reimbursement, status " +
                    "where reimbursement.employee_num = ? AND status.id = reimbursement.status_type_id AND reimbursement.status_type_id = ?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, employee_num);
            ps.setInt(2, status);
        } else {
            String sql = "Select distinct status, date_time, amount,(" +
                    "Select first_name from employee where employee_num = manager_num), (" +
                    "Select last_name from employee where employee_num = manager_num), note " +
                    "from employee, reimbursement, status " +
                    "where reimbursement.employee_num = ? AND status.id = reimbursement.status_type_id";
            ps = c.prepareStatement(sql);
            ps.setInt(1, employee_num);
        }

        //Execute
        rs = ps.executeQuery();

        while (rs.next()) {
            r = new Reimbursement();
            r = setUserReimbursement(rs, r);
            rList.add(r);
        }

        c.commit();
        logger.info("Returning Reimbursement List from one user.");
        return rList;

    } catch (SQLException ex) {
        ex.printStackTrace();
        if(c != null) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    } finally {
        closeConnection(c, ps, rs);
    }

        return rList;
} // findOne method

    public List<Reimbursement> findAll(int status, int user) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reimbursement r;
        List<Reimbursement> rList = new ArrayList<>();

        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            if (status != 0 && user == 0) {
                String sql = "Select distinct reimbursement.id, status, first_name, last_name, date_time, amount,(" +
                        "Select first_name from employee where employee_num = manager_num) as manager_first_name, (" +
                        "Select last_name from employee where employee_num = manager_num) as manager_last_name, note " +
                        "from employee, reimbursement, status " +
                        "WHERE reimbursement.status_type_id = status.id " +
                        "And employee.employee_num = reimbursement.employee_num AND reimbursement.status_type_id = ?";
                ps = c.prepareStatement(sql);
                ps.setInt(1, status);
            } else if(status == 0 && user != 0) {
                String sql = "Select distinct reimbursement.id, status, first_name, last_name, date_time, amount,(" +
                        "Select first_name from employee where employee_num = manager_num) as manager_first_name, (" +
                        "Select last_name from employee where employee_num = manager_num) as manager_last_name, note " +
                        "from employee, reimbursement, status " +
                        "WHERE reimbursement.status_type_id = status.id " +
                        "And employee.employee_num = reimbursement.employee_num " +
                        "AND employee.employee_num = ?";
                ps = c.prepareStatement(sql);
                ps.setInt(1, user);
            } else if (status != 0 && user != 0) {
                String sql = "Select distinct reimbursement.id, status, first_name, last_name, date_time, amount,(" +
                        "Select first_name from employee where employee_num = manager_num) as manager_first_name, (" +
                        "Select last_name from employee where employee_num = manager_num) as manager_last_name, note " +
                        "from employee, reimbursement, status " +
                        "WHERE reimbursement.status_type_id = status.id " +
                        "And employee.employee_num = reimbursement.employee_num " +
                        "AND reimbursement.status_type_id = ? AND employee.employee_num = ?";
                ps = c.prepareStatement(sql);
                ps.setInt(1, status);
                ps.setInt(2, user);
            } else {
                String sql = "Select distinct reimbursement.id, status, first_name, last_name, date_time, amount,(" +
                        "Select first_name from employee where employee_num = manager_num) as manager_first_name, (" +
                        "Select last_name from employee where employee_num = manager_num) as manager_last_name, note " +
                        "from employee, reimbursement, status " +
                        "WHERE reimbursement.status_type_id = status.id And employee.employee_num = reimbursement.employee_num";
                ps = c.prepareStatement(sql);
            }

            //Execute
            rs = ps.executeQuery();

            while (rs.next()) {
                r = new Reimbursement();
                r = setAdminReimbursement(rs, r);
                rList.add(r);
            }

            c.commit();
            logger.info("Returning Reimbursement List from all users");
            return rList;

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(c != null) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            closeConnection(c, ps, rs);
        }

        return rList;
    } // findOne method

    public void addReimbursement(Reimbursement r) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "insert into reimbursement (employee_num, status_type_id, amount, date_time, note) " +
                    "values (?, 3, ?, ?, ?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1, r.getEmployeeNum());
            ps.setFloat(2, r.getAmount());
            ps.setString(3, r.getDate_time());
            ps.setString(4, r.getNote());

            //Execute
            ps.executeUpdate();

            c.commit();
            logger.info("Added new Reimbursement for one user.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(c != null) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            closeConnection(c, ps);
        }
    } // addReimbursement

    public void updateReimbursement(Reimbursement r) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "UPDATE reimbursement SET manager_num = ?, status_type_id = ? WHERE id = ?;";
            ps = c.prepareStatement(sql);
            ps.setInt(1, r.getManagerNum());
            if (r.getType() == ReimbursementStatus.APPROVED) ps.setInt(2, 1);
            else if (r.getType() == ReimbursementStatus.REJECTED) ps.setInt(2, 2);
            else ps.setInt(2, 3);
            ps.setInt(3, r.getId());

            //Execute
            ps.executeUpdate();
            c.commit();

            String qry = "Select amount, note, status, email, last_name from reimbursement, status, employee " +
                    "where reimbursement.id = ? AND status.id = reimbursement.status_type_id " +
                    "AND reimbursement.employee_num = employee.employee_num";
            ps = c.prepareStatement(qry);
            ps.setInt(1, r.getId());

            //Execute
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                r.setAmount(rs.getFloat("amount"));
                r.setNote(rs.getString("note"));
                Employee e = new Employee();
                e.setEmail(rs.getString("email"));
                e.setLastName(rs.getString("last_name"));
                Mail.sendReimbursement(r, e);
            }

            c.commit();
            logger.info("Updated reimbursement.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(c != null) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            closeConnection(c, ps);
        }
    } // updateReimbursement

    // Private Methods
    private Reimbursement setUserReimbursement(ResultSet rs, Reimbursement r){
        try {
            r = setReimbursementType(rs, r);
            r.setAmount(rs.getFloat("amount"));
            r.setDate_time(rs.getString("date_time"));
            r.setManagerFirstName(rs.getString("first_name"));
            r.setManagerLastName(rs.getString("last_name"));
            r.setNote(rs.getString("note"));
        } catch (SQLException e) {}

        return r;
    } // setUserReimbursement

    private Reimbursement setAdminReimbursement(ResultSet rs, Reimbursement r){
        try {
            r = setReimbursementType(rs, r);
            r.setId(rs.getInt("id"));
            r.setAmount(rs.getFloat("amount"));
            r.setDate_time(rs.getString("date_time"));
            r.setEmployeeFirstName(rs.getString("first_name"));
            r.setEmployeeLastName(rs.getString("last_name"));
            r.setManagerFirstName(rs.getString("manager_first_name"));
            r.setManagerLastName(rs.getString("manager_last_name"));
            r.setNote(rs.getString("note"));
        } catch (SQLException e) {}

        return r;
    } // setAdminReimbursement

    private Reimbursement setReimbursementType(ResultSet rs, Reimbursement r) {
        try {
            String type = rs.getString("status");
            if(type.equalsIgnoreCase(ReimbursementStatus.APPROVED.toString())) r.setType(ReimbursementStatus.APPROVED);
            else if (type.equalsIgnoreCase(ReimbursementStatus.PENDING.toString())) r.setType(ReimbursementStatus.PENDING);
            else if (type.equalsIgnoreCase(ReimbursementStatus.REJECTED.toString())) r.setType(ReimbursementStatus.REJECTED);
            else r.setType(ReimbursementStatus.UNKNOWN);
        } catch (SQLException e) {}

        return r;
    } // setReimbursementType

    // Close Database Connections
    public static void closeConnection(Connection c, PreparedStatement ps) {
        if (c != null) {
            try {
                ps.close();
                c.setAutoCommit(true);
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // closeConnection method

    public static void closeConnection(Connection c, PreparedStatement ps, ResultSet rs) {
        if (c != null) {
            try {
                ps.close();
                rs.close();
                c.setAutoCommit(true);
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // closeConnection method

}