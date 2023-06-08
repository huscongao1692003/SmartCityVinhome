package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.model.Person;
import com.thanhnguyen.smartCity.model.Service;
import com.thanhnguyen.smartCity.repository.PersonRepository;
import com.thanhnguyen.smartCity.repository.ServiceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class PriceController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ServiceRepository serviceRepository;


    @RequestMapping(value={"/pricing"})
    public String displayPricePage() {
        return "pricing.html";
    }

    @RequestMapping(value={"/basic"} , method= RequestMethod.POST)
    public String basicService(Authentication authentication, HttpSession session){
        Optional<Service> service = serviceRepository.findById(1);
        Person personEntity = personRepository.readByEmail(authentication.getName());
        personEntity.setService(service.get());
        personRepository.save(personEntity);
        service.get().getPersons().add(personEntity);
        serviceRepository.save(service.get());
        return "pricing.html";
    }
    @RequestMapping(value={"/pro"})
    public String proService(){
        return "pricing.html";
    }
}
