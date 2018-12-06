package com.balvee.daoimplements;

import com.balvee.models.Employee;
import com.balvee.models.EmployeeTypes;
import com.balvee.models.Mail;
import com.balvee.models.Password;
import com.balvee.util.DBConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl {

    static Logger logger = Logger.getLogger(EmployeeDaoImpl.class);

    // Database Actions

    public Employee findOne(String usr_name, String password) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee e = null;

        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "SELECT employee_num, first_name, last_name, usr_name, password, employee_type " +
                         "FROM employee, employee_type " +
                         "WHERE usr_name = ? AND employee.employee_type_id = employee_type.id";
            ps = c.prepareStatement(sql);
            ps.setString(1, usr_name);

            //Execute
            rs = ps.executeQuery();

            while (rs.next()) {
                if(Password.check(password, rs.getString("password"))) {
                    e = new Employee();
                    e = setEmployee(rs, e);
                }
            }

            c.commit();
            logger.info("Employee Found.");
            return e;

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(c != null) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e1) {
            //e1.printStackTrace();
        } finally {
            ReimbursementDaoImpl.closeConnection(c, ps, rs);
        }

        logger.info("Employee not found.");
        return e;
    } // findOne method

    public Employee findOne(int employeeNum) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee e = null;

        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "SELECT employee_num, first_name, last_name, usr_name, employee_type, address, phone, email " +
                    "FROM employee, employee_type " +
                    "WHERE employee_num = ? AND employee.employee_type_id = employee_type.id";
            ps = c.prepareStatement(sql);
            ps.setInt(1,employeeNum);

            //Execute
            rs = ps.executeQuery();

            while (rs.next()) {
                e = new Employee();
                e = setEmployee(rs, e);
            }

            c.commit();
            logger.info("Employee Found.");
            return e;

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
            ReimbursementDaoImpl.closeConnection(c, ps, rs);
        }

        logger.info("Employee not found.");
        return e;
    } // findOne method

    public List<Employee> findAll() {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee e = null;
        List<Employee> eList = new ArrayList<>();

        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "SELECT employee_num, first_name, last_name, usr_name, password, employee_type " +
                    "FROM employee, employee_type " +
                    "WHERE employee.employee_type_id = employee_type.id";
            ps = c.prepareStatement(sql);

            //Execute
            rs = ps.executeQuery();

            while (rs.next()) {
                e = new Employee();
                e = setEmployeeType(rs, e);
                e = setEmployee(rs, e);
                eList.add(e);
            }

            c.commit();
            return eList;

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
            ReimbursementDaoImpl.closeConnection(c, ps, rs);
        }

        return eList;
    } // findAll method

    public void addEmployee(Employee e){
        Connection c = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            c = DBConnectionUtil.newConnection();
            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "insert into employee (employee_num, first_name, last_name, usr_name, password, employee_type_id, email) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1, e.getEmployeeNum());
            ps.setString(2, e.getFirstName());
            ps.setString(3, e.getLastName());
            ps.setString(4, e.getUsername());
            ps.setString(5, Password.getSaltedHash(e.getPassword()));
            if(e.getType() == EmployeeTypes.MANAGER) {
                ps.setInt(6,2);
            } else {
                ps.setInt(6,1);
            }
            ps.setString(7, e.getEmail());

            //Execute
            ps.executeUpdate();
            c.commit();

            Mail.sendCreditials(e);

            if(e.getAddress() != "") {
                String query = "update employee set address = ? where employee_num = ?";
                ps1 = c.prepareStatement(query);
                ps1.setString(1, e.getAddress());
                ps1.setInt(2, e.getEmployeeNum());
                ps1.executeUpdate();
                c.commit();
            }

            if(e.getPhone() != "") {
                String qry = "update employee set phone = ? where employee_num = ?";
                ps2 = c.prepareStatement(qry);
                ps2.setString(1, e.getPhone());
                ps2.setInt(2, e.getEmployeeNum());
                ps2.executeUpdate();
                c.commit();
            }

            logger.info("Added new Employee.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(c != null) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e1) {
            //e1.printStackTrace();
        } finally {
            ReimbursementDaoImpl.closeConnection(c, ps);
        }
    } // addEmployee

    public void updateUser(Employee e) {
        Connection c = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;


        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "update employee set first_name = ?, last_name = ? where employee_num = ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setInt(3, e.getEmployeeNum());

            //Execute
            ps.executeUpdate();
            c.commit();

            if(e.getPassword() != null){
                String qrys = "update employee set password = ? where employee_num = ?";
                ps1 = c.prepareStatement(qrys);
                ps1.setString(1, Password.getSaltedHash(e.getPassword()));
                ps1.setInt(2, e.getEmployeeNum());
                ps1.executeUpdate();
                c.commit();
            }

            if(e.getEmail() != null){
                String qry = "update employee set email = ? where employee_num = ?";
                ps2 = c.prepareStatement(qry);
                ps2.setString(1, e.getEmail());
                ps2.setInt(2, e.getEmployeeNum());
                ps2.executeUpdate();
                c.commit();
            }

            if(e.getAddress() != null) {
                String query = "update employee set address = ? where employee_num = ?";
                ps3 = c.prepareStatement(query);
                ps3.setString(1, e.getAddress());
                ps3.setInt(2, e.getEmployeeNum());
                ps3.executeUpdate();
                c.commit();
            }

            if(e.getPhone() != null) {
                String qry = "update employee set phone = ? where employee_num = ?";
                ps4 = c.prepareStatement(qry);
                ps4.setString(1, e.getPhone());
                ps4.setInt(2, e.getEmployeeNum());
                ps4.executeUpdate();
                c.commit();
            }


            logger.info("Updated Employee.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(c != null) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            ReimbursementDaoImpl.closeConnection(c, ps);
        }
    } // updateUser

    public void forgotPassword(Employee e) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = DBConnectionUtil.newConnection();

            //turn of autocommit
            c.setAutoCommit(false);
            String sql = "update employee set password = ? where email = ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, Password.getSaltedHash(e.getPassword()));
            ps.setString(2, e.getEmail());

            //Execute
            ps.executeUpdate();
            c.commit();

            logger.info("Updated Employee Password.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(c != null) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            ReimbursementDaoImpl.closeConnection(c, ps);
        }
    } // forgotPassword

    // Private Methods

    private Employee setEmployee(ResultSet rs, Employee e){
        try {
            e = setEmployeeType(rs, e);
            e.setEmployeeNum(rs.getInt("employee_num"));
            e.setFirstName(rs.getString("first_name"));
            e.setLastName(rs.getString("last_name"));
            e.setUsername(rs.getString("usr_name"));
            e.setPhone(rs.getString("phone"));
            e.setAddress(rs.getString("address"));
            e.setEmail(rs.getString("email"));
        } catch (SQLException ex) {}

        return e;
    } // setEmployee

    private Employee setEmployeeType(ResultSet rs, Employee e) {
        try {
            String type = rs.getString("employee_type");
            if(type.equalsIgnoreCase(EmployeeTypes.EMPLOYEE.toString())) e.setType(EmployeeTypes.EMPLOYEE);
            else if (type.equalsIgnoreCase(EmployeeTypes.MANAGER.toString())) e.setType(EmployeeTypes.MANAGER);
            else e.setType(EmployeeTypes.UNKNOWN);
        } catch (SQLException ex) {}

        return e;
    } // setEmployeeType

}// EmployeeDaoImpl
