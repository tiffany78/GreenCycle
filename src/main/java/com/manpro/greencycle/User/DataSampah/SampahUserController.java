package com.manpro.greencycle.User.DataSampah;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/")
public class SampahUserController {
    @Autowired
    private SampahUserRepository repo;

    @GetMapping("/DataSampah")
    public String dataSampah(@RequestParam(value = "filter", required = false) String filter, Model model) {
        List<SampahUser> list = this.repo.findAll();

        if (filter != null && !filter.isEmpty()) {
            list = this.repo.filterSampah(filter);
            model.addAttribute("filter", filter);
        }

        model.addAttribute("sampah", list);
        return "user/DataSampah/index";
    }

    @GetMapping("/LandingPage")
    public String home() {
        return "user/LandingPage/index";
    }
}
