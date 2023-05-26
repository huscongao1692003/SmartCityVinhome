package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.model.Person;
import com.thanhnguyen.smartCity.model.Product;
import com.thanhnguyen.smartCity.model.Service;
import com.thanhnguyen.smartCity.repository.PersonRepository;
import com.thanhnguyen.smartCity.repository.ProductRepository;
import com.thanhnguyen.smartCity.repository.ServiceRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    PersonRepository personRepository;


    // Function for Service
    @RequestMapping("/displayServices")
    public ModelAndView displayClasses(Model model) {
        List<Service> service = serviceRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("service.html");
        modelAndView.addObject("services",service);
        modelAndView.addObject("service", new Service());
        return modelAndView;
    }

    @PostMapping("/addNewService")
    public ModelAndView addNewClass(Model model, @ModelAttribute("service") Service service) {
        serviceRepository.save(service);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayServices");
        return modelAndView;
    }

    @RequestMapping("/deleteService")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<Service> service = serviceRepository.findById(id);
        for(Person person : service.get().getPersons()){
            person.setService(null);
            personRepository.save(person);
        }
        serviceRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayServices");
        return modelAndView;
    }

    @GetMapping("/displayUsers")
    public ModelAndView displayStudents(Model model, @RequestParam int serId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("user.html");
        Optional<Service> service = serviceRepository.findById(serId);
        modelAndView.addObject("service",service.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("service",service.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addUser")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Service service = (Service) session.getAttribute("service");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayUsers?serviceId="+service.getServiceId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.setService(service);
        personRepository.save(personEntity);
        service.getPersons().add(personEntity);
        serviceRepository.save(service);
        modelAndView.setViewName("redirect:/admin/displayStudents?serviceId="+service.getServiceId());
        return modelAndView;
    }

    @GetMapping("/deleteUser")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        Service service = (Service) session.getAttribute("service");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setService(null);
        service.getPersons().remove(person.get());
        Service serviceSaved = serviceRepository.save(service);
        session.setAttribute("service",serviceSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayUsers?serviceId="+service.getServiceId());
        return modelAndView;
    }

    // CRUD Account By Admin
    @GetMapping("/displayAccount")
    public ModelAndView displayAccount(Model model){
        List<Person> person = personRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("account.html");
        modelAndView.addObject("account", person);
        return modelAndView;
    }
    @GetMapping("/removeAccount")
    public String removeAccount(@RequestParam int id) {
        personRepository.deleteById(id);
        return "redirect:/admin/displayAccount";
    }


}
