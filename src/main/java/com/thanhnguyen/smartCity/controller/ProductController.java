package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.model.Person;
import com.thanhnguyen.smartCity.model.Product;
import com.thanhnguyen.smartCity.repository.PersonRepository;
import com.thanhnguyen.smartCity.repository.ProductRepository;
import com.thanhnguyen.smartCity.service.ProductService;
import com.thanhnguyen.smartCity.ultils.ImageUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private ProductService productService;

    @RequestMapping(value={"/product"})
    public ModelAndView displayProductPage(HttpSession session) {
        List<Product> products = productService.getAllProduct();
        ModelAndView modelAndView = new ModelAndView("products.html");
        modelAndView.addObject("products",products);
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("cart", session.getAttribute("cart"));
        modelAndView.addObject("totalItems", session.getAttribute("totalItems"));
        modelAndView.addObject("totalPrice",session.getAttribute("totalPrice"));
        return modelAndView;
    }
    //Function of Product
    @GetMapping("/displayProduct")
    public ModelAndView displayProduct(Model model) {
        List<Product> products = productService.getAllProduct();
        List<String> urlSafeImageData = new ArrayList<>();
        for (Product product : products) {
            byte[] imageData = product.getImage().getBytes();
            String imageDataList = Base64.getEncoder().encodeToString(imageData);
            urlSafeImageData.add(imageDataList);
        }
        ModelAndView modelAndView = new ModelAndView("product_secure.html");
        modelAndView.addObject("urlSafeImageData", urlSafeImageData);
        modelAndView.addObject("products", products);
        modelAndView.addObject("product", new Product());

        return modelAndView;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String saveProduct(@RequestParam("image") MultipartFile file,
                              @RequestParam("name") String name,
                              @RequestParam("fees") int fees)
    {
        productService.saveProductToDB(file, name, fees);
        return "redirect:/displayProduct";
    }

    @RequestMapping(value = "/deleteProduct",method = GET)
    public ModelAndView deleteProduct(Model model ,@RequestParam int id)
    {
        Optional<Product> product = productRepository.findById(id);
        for(Person person : product.get().getPersons()){
            person.setProducts(null);
            personRepository.save(person);
        }
        productRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/displayProduct");
        return modelAndView;
    }
    @PostMapping("/changeName")
    public String changePname(@RequestParam("id") int id,
                              @RequestParam("newName") String name)
    {
        productService.chageProductName(id, name);
        return "redirect:/displayProduct";
    }

    @PostMapping("/changePrice")
    public String changePrice(@RequestParam("id") int id ,
                              @RequestParam("newPrice") int fees)
    {
        productService.changeProductPrice(id, fees);
        return "redirect:/displayProduct";
    }

//    @PostMapping("/addNewCourse")
//    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
//        ModelAndView modelAndView = new ModelAndView();
//        coursesRepository.save(course);
//        modelAndView.setViewName("redirect:/admin/displayCourses");
//        return modelAndView;
//    }
//
//    @GetMapping("/viewStudents")
//    public ModelAndView viewStudents(Model model, @RequestParam int id
//            ,HttpSession session,@RequestParam(required = false) String error) {
//        String errorMessage = null;
//        ModelAndView modelAndView = new ModelAndView("course_students.html");
//        Optional<Courses> courses = coursesRepository.findById(id);
//        modelAndView.addObject("courses",courses.get());
//        modelAndView.addObject("person",new Person());
//        session.setAttribute("courses",courses.get());
//        if(error != null) {
//            errorMessage = "Invalid Email entered!!";
//            modelAndView.addObject("errorMessage", errorMessage);
//        }
//        return modelAndView;
//    }
//
//    @PostMapping("/addStudentToCourse")
//    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person,
//                                           HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView();
//        Courses courses = (Courses) session.getAttribute("courses");
//        Person personEntity = personRepository.readByEmail(person.getEmail());
//        if(personEntity==null || !(personEntity.getPersonId()>0)){
//            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()
//                    +"&error=true");
//            return modelAndView;
//        }
//        personEntity.getCourses().add(courses);
//        courses.getPersons().add(personEntity);
//        personRepository.save(personEntity);
//        session.setAttribute("courses",courses);
//        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
//        return modelAndView;
//    }
//
//    @GetMapping("/deleteStudentFromCourse")
//    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId,
//                                                HttpSession session) {
//        Courses courses = (Courses) session.getAttribute("courses");
//        Optional<Person> person = personRepository.findById(personId);
//        person.get().getCourses().remove(courses);
//        courses.getPersons().remove(person);
//        personRepository.save(person.get());
//        session.setAttribute("courses",courses);
//        ModelAndView modelAndView = new
//                ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
//        return modelAndView;
//    }


}