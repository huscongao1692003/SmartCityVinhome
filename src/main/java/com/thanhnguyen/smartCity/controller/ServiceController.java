package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.model.Person;
import com.thanhnguyen.smartCity.model.Service;
import com.thanhnguyen.smartCity.repository.PersonRepository;
import com.thanhnguyen.smartCity.repository.ServiceRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ServiceController {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/viewService")
    public ModelAndView displayService(Model model, Authentication authentication) {
        Person person = personRepository.readByEmail(authentication.getName());
        Optional<Service> service = serviceRepository.findById(person.getService().getServiceId());
        ModelAndView modelAndView = new ModelAndView("service_enroll.html");
        modelAndView.addObject("services",service.get());
        modelAndView.addObject("service", new Service());
        return modelAndView;
    }
}
