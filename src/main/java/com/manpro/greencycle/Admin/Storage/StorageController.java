package com.manpro.greencycle.Admin.Storage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class StorageController {

    @GetMapping("/Storage")
    public String storage() {
        return "admin/Storage/index"; // Mengacu pada src/main/resources/templates/index.html
    }
}

