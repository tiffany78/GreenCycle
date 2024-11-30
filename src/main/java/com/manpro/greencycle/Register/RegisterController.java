package com.manpro.greencycle.Register;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterRepository repo;

    @GetMapping
    public String register(){
        return "user/Register/index1";
    }

    @GetMapping("/page2")
    public String register2(Model model){
        List<Kecamatan> list = this.repo.getAllKec();
        model.addAttribute("kecamatanList", list);
        return "user/Register/index2";
    }
}
