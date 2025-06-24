package com.website;

import javax.servlet.annotation.WebServlet;
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
@WebServlet("/Payment")
public class Payment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      User user = (User) request.getSession().getAttribute("user");
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        String cvv = request.getParameter("cvv");

        String sql = "INSERT INTO payment (email, card_holder, card_number, cvv) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, user.getEmail());
            stmt.setString(2, cardName);
            stmt.setString(3, cardNumber);
            stmt.setString(4, cvv);


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
sendpaymentEmail(user.getEmail(), user.getFirstName());
                request.getRequestDispatcher("/main.jsp").forward(request, response);

            } else {

                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Payment failed! Please try again.');");
                request.getRequestDispatcher("/payment.html").forward(request, response);
                    out.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendpaymentEmail(String customerEmail, String name) {
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
            String subject = "Payment Successful! Welcome to NutriTrack";

            String body = "Dear " + name + ",\n\n" +
                    "Thank you for your payment. Your NutriTrack subscription is now active.\n" +
                    "You can start using your personalized nutrition plans and connect with your coach anytime.\n\n" +
                    "Best regards,\n" +
                    "The NutriTrack Team";




            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);


        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    }

