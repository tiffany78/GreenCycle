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
}

