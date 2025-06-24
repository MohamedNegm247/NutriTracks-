package com.website;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class SendEmail {
    public String  getRandom(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);


        return String.format("%06d", number);

    }
    public boolean sendEmail(User user){
        boolean flag = false;
        String toEmail = user.getEmail();
        String fromEmail = "mnegm6617@gmail.com";
        String password = "starlord12354";

        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "465");  // SSL port
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.ssl.enable", "true");
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setFrom(new InternetAddress(fromEmail));
            message.setSubject("Welcome to website - Verification Code");
            message.setText("Welcome to our website. Your verification code is: " + user.getCode());

            Transport.send(message);
            flag = true;

        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
            e.printStackTrace(); // More detailed error
        }

        return flag;
    }


}
