package com.balvee.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    // Initialize Database variables

    static Logger logger = Logger.getLogger(DBConnectionUtil.class);
    private static String url = "jdbc:postgresql://brian-alvarez-code.cmtnhku1qtqt.us-east-2.rds.amazonaws.com:5432/project_1";
    private static String username = "balvare";
    private static String password = "Abdiel90";

    //legacy support

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            logger.info("Database connected.");
        } catch (SQLException e) {
            logger.error("Database unable to connect.");
            e.printStackTrace();
        }
    }

    public static Connection newConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    } // newConnection
} // DBConnectionUtil class