package com.thanhnguyen.smartCity.mailer;

import com.thanhnguyen.smartCity.model.Subscribe;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mail {
    private final String PORT = "587";
    private final String HOST = "sandbox.smtp.mailtrap.io";
    private final String USERNAME = "a20ba20cc22d02";
    private final String PASSWORD = "7158e0cc3f154a";
    private final String EMAIL = "0b5d29e626-65f490@inbox.mailtrap.io";

    private final boolean AUTH = true;
    private final boolean STARTTLS = true;

    public void send(Subscribe subscriber) throws AddressException, MessagingException, IOException {
        Message msg = new MimeMessage(setSession(setProperties()));

        msg.setSentDate(new Date());
        msg.setSubject("You're subscribed on newsletter");

        msg.setFrom(new InternetAddress(EMAIL, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(subscriber.getEmail()));

        msg.setContent(subscriber.getEmail().concat(", you're very welcome here!"), "text/html");

        Transport.send(msg);
    }

    private Session setSession(Properties props) {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        return session;
    }

    private Properties setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);

        return props;
    }
}