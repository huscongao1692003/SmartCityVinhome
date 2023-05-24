package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.model.Person;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        ModelAndView modelAndView = new ModelAndView("service_enroll.html");
        modelAndView.addObject("person",person);
        return modelAndView;
    }


}

