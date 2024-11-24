package com.manpro.greencycle.Admin.Member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")

public class MemberController {
    @GetMapping("/InformasiMember")
    public String setoranSampah() {
        return "admin/InformasiMember/index";
    }
}
