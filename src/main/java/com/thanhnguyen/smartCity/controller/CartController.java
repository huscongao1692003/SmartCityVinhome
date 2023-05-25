package com.thanhnguyen.smartCity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
    @RequestMapping(value={ "/cart"})
    public String displayHomePage() {
        return "shopping_cart.html";
    }
}
