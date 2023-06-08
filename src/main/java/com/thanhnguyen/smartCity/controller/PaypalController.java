package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.config.PaypalPaymentIntent;
import com.thanhnguyen.smartCity.config.PaypalPaymentMethod;
import com.thanhnguyen.smartCity.model.Item;
import com.thanhnguyen.smartCity.model.Order;
import com.thanhnguyen.smartCity.model.Person;
import com.thanhnguyen.smartCity.repository.OrderRepository;
import com.thanhnguyen.smartCity.repository.PersonRepository;
import com.thanhnguyen.smartCity.service.PaypalService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.List;


@Controller
public class PaypalController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PersonRepository personRepository;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;


    @PostMapping("/pay")
    public String pay(@RequestParam("price") double price ){
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.order,
                    "payment description",
                    "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
    @GetMapping(CANCEL_URL)
    public String cancelPay(){
        return "cancel";
    }
    @GetMapping(SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session, Authentication authentication){
        Order order = new Order();
        Person person = personRepository.readByEmail(authentication.getName());
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                double totalPrice= (Double)session.getAttribute("totalPrice");
                List<Item> cart = (List<Item>) session.getAttribute("cart");
                order.setPrice((int)totalPrice);
                order.setCurrency("USD");
                order.setMethod("Paypal");
                order.setIntent(payment.getIntent());
                order.setCustomerName(person.getName());
                order.setCustomerEmail(person.getEmail());
                orderRepository.save(order);
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        session.invalidate();
        return "redirect:/";
    }
}
