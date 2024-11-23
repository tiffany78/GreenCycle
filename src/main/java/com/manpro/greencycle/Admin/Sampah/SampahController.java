package com.manpro.greencycle.Admin.Sampah;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")

public class SampahController {
    @GetMapping("/DataSampah")
    public String dataSampah() {
        return "admin/DataSampah/index";
    }

    @GetMapping("/TambahSampah")
    public String tambahSampah() {
        return "admin/TambahSampah/index";
    }

    @GetMapping("/EditHarga")
    public String editHarga() {
        return "admin/EditHarga/index";
    }

    @GetMapping("/LandingPage")
    public String home(){
        return "admin/LandingPage/index";
    }
}
