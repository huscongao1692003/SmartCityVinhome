package com.thanhnguyen.smartCity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PriceController {
    @RequestMapping(value={"/pricing"})
    public String displayPricePage() {
        return "pricing.html";
    }

    @RequestMapping(value={"/basic"})
    public String basicService(){
        return "pricing.html";
    }
    @RequestMapping(value={"/pro"})
    public String proService(){
        return "pricing.html";
    }
}
