package com.balvee.models;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {
    /**
     * @param args the command line arguments
     */
    public Mail() {}

    public static void sendPassword(Employee e) {
        //Email to send to
        String to = e.getEmail();
        //Email to be sent from
        String from = "patriots.sysinfo@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "B1o1s1t1o1n");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(e.getEmail()));
            message.setSubject("Forgot Your Password?!?");
            message.setContent("<div>" +
                            "<h1>Thank you for contacting us.</h1>" +
                            "<h4>Your temporary password is "+ e.getPassword() +".</h4>" +
                            "<h6>Please update your password immediately.</h6>" +
                            "</div>"
                    , "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }// sendPassword

    public static void sendCreditials(Employee e) {
        //Email to send to
        String to = e.getEmail();
        //Email to be sent from
        String from = "patriots.sysinfo@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "B1o1s1t1o1n");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(e.getEmail()));
            message.setSubject("Welcome " + e.getFirstName());
            message.setContent("<div>" +
                            "<h1>Thank you joining the team.</h1>" +
                            "<h4>Your username is "+ e.getUsername() +".</h4>" +
                            "<h4>and your temporary password is <strong>"+ e.getPassword() +"<strong>.</h4>" +
                            "<h6>Please update your password immediately.</h6>" +
                            "</div>"
                    , "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }// sendCredintials

    public static void sendReimbursement(Reimbursement r, Employee e) {
        //Email to send to
        String to = e.getEmail();
        //Email to be sent from
        String from = "patriots.sysinfo@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "B1o1s1t1o1n");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(e.getEmail()));
            message.setSubject("Reimbursement Update");
            message.setContent("<div>" +
                            "<h1>Your reimbursement has been updated.</h1>" +
                            "<h4>The reimbursement for " + r.getNote() + " in the amount of $" + r.getAmount() + " has been " + r.getType() +"</h4>" +
                            "<h4>For any questions please speak with your supervisor.</h4>" +
                            "<h6>Thank you.</h6>" +
                            "</div>"
                    , "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }// sendReimbursement

}// Mail