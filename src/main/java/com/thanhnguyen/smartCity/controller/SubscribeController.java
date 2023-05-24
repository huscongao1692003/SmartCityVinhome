package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.mailer.Mail;
import com.thanhnguyen.smartCity.model.Subscribe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

@Controller
public class SubscribeController {
    @PostMapping("/subscribe")
    public String submitForm(Subscribe subscribeForm) throws AddressException, MessagingException, IOException {
        Mail mailer = new Mail();
        mailer.send(subscribeForm);
        return "redirect:/home";
    }
}
