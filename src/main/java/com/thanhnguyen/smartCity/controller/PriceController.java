package com.thanhnguyen.smartCity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PriceController {
    @RequestMapping(value={"/pricing"})
    public String displayPricePage() {
        return "pricing.html";
    }
}
