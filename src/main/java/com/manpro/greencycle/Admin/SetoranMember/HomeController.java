package com.manpro.greencycle.Admin.SetoranMember;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class HomeController {

    @GetMapping("/STMember")
    public String setoranMember() {
        return "admin/SetoranMember/index"; // Mengacu pada src/main/resources/templates/index.html
    }
    @GetMapping("/TSMember")
    public String tambahMember() {
        return "admin/TambahSetoranMember/index"; // Mengacu pada src/main/resources/templates/index.html
    }
    @GetMapping("/Storage")
    public String storage() {
        return "admin/Storage/index"; // Mengacu pada src/main/resources/templates/index.html
    }
    @GetMapping("/Login")
    public String loginAdmin() {
        return "admin/Login/index"; // Mengacu pada src/main/resources/templates/index.html
    }
}

