package com.thanhnguyen.smartCity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DonateController {
    @RequestMapping(value={"/donate"})
    public String displayHomePage() {
        return "donate.html";
    }
}
