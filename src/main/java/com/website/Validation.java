package com.website;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet("/Validation")
public class Validation extends HttpServlet  {



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String gender = request.getParameter("gender");
String firstName = request.getParameter("firstName");
String lastName = request.getParameter("lastName");
String street = request.getParameter("street");
String building = request.getParameter("building");
double weight = Double.parseDouble(request.getParameter("weight"));
double height = Double.parseDouble(request.getParameter("height"));
User user = new User(firstName,lastName,email,password,street,weight,height,gender,building);
        if (!password.equals(confirmPassword)) {

            out.println("<script>");
            out.println("alert('Passwords do not match');");
            out.println("window.location.href = 'Signup.html?error=password';");
            out.println("</script>");
    } else try (Connection conn = DatabaseManager.getConnection()) {
            String checkSql = "SELECT * FROM user WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {

                response.sendRedirect("Login.html");
            } else {

                String insertSql = "INSERT INTO user (email, password, gender, first_name, last_name, street, building, weight, height) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, email);
                insertStmt.setString(2, password);
                insertStmt.setString(3, gender);
                insertStmt.setString(4, firstName);
                insertStmt.setString(5, lastName);
                insertStmt.setString(6, street);
                insertStmt.setString(7, building);
                insertStmt.setDouble(8, weight);
                insertStmt.setDouble(9, height);
sendEmail(email,firstName);
                int rowsInserted = insertStmt.executeUpdate();

                System.out.println("Rows inserted: " + rowsInserted);
                if (rowsInserted > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    request.getRequestDispatcher("payment.html").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to register user.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
}
    private void sendEmail(String customer, String name) {
        String host = "smtp.gmail.com";
        String fromEmail = "mnegm6617@gmail.com";
        String appPassword = "ytug blnd epiu ndhl";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            String subject = "Welcome to NutriTrack! Your Health Journey Starts Here";

            String body = "Dear " + name + ",\n\n" +
                    "Welcome to NutriTrack! We're excited to have you on board.\n\n" +
                    "NutriTrack is your personal nutrition companion designed to help you achieve your health goals. " +
                    "Whether you want to lose weight, build muscle, or simply eat healthier, our expert coaches and tailored meal plans are here to support you every step of the way.\n\n" +
                    "Log in to your NutriTrack account to explore personalized nutrition advice, track your progress, and connect with your dedicated coach.\n\n" +
                    "Thank you for choosing NutriTrack to be part of your wellness journey. We're here to help you stay motivated and healthy!\n\n" +
                    "Best regards,\n" +
                    "The NutriTrack Team";



            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(customer));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);


        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
