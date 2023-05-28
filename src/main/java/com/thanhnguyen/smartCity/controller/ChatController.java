package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.model.ChatMessage;
import com.thanhnguyen.smartCity.model.Person;
import com.thanhnguyen.smartCity.model.Profile;
import com.thanhnguyen.smartCity.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ComponentScan(basePackages = {"com.thanhnguyen.smartCity"})
public class ChatController {

    @Autowired
    PersonRepository personRepository;
    @RequestMapping("/messsage")
    public ModelAndView chatPage(Model model, HttpSession session, Authentication authentication){
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        ModelAndView modelAndView = new ModelAndView("chat.html");
        return modelAndView;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}