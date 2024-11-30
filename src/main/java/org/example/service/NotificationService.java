package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class NotificationService {

    static final Logger logger = (Logger) LogManager.getLogger("myLogger");

    private static String EMAIL_USERNAME;
    private static String EMAIL_APP_PASSWORD;

    public static void sendEmail(String to, String subject, String messageBody) throws IOException {
        // load configuration properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

        EMAIL_USERNAME = properties.getProperty("email.username");
        EMAIL_APP_PASSWORD = properties.getProperty("email.password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_APP_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            logger.info("Email sent successfully to " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

