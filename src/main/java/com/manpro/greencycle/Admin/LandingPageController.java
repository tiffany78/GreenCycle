package com.manpro.greencycle.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LandingPageController {
    
    @GetMapping
    public String index(){
        return "/admin/LandingPage/index";
    }
}
