package com.manpro.greencycle.Admin.Sampah;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/DataSampah")

public class SampahController {
    @GetMapping("/")
    public String index() {
        return "admin/DataSampah/index";
    }
}
