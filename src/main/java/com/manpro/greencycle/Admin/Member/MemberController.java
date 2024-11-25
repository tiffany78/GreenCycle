package com.manpro.greencycle.Admin.Member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/")

public class MemberController {
    @Autowired
    JDBCMemberRepository memberRepository;

    @GetMapping("/InformasiMember")
    public String setoranSampah(Model model,
            @RequestParam(defaultValue = "", required = false) String filter) {
        
        List<Member> memberList = memberRepository.findAll();
        model.addAttribute("members", memberList);
        return "admin/InformasiMember/index";
    }
}
