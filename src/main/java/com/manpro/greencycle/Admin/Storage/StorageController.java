package com.manpro.greencycle.Admin.Storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/")
public class StorageController {

    @Autowired
    JDBCStorageRepository storageRepo;

    @GetMapping("/Storage")
    public String storage(Model model,
            @RequestParam(defaultValue = "", required =  false) String filter) {
        
        List<Storage> sampahList = null;
        if(filter.length() == 0){
            sampahList = storageRepo.findAll();
        }
        else{
            sampahList = storageRepo.findWithFilter(filter);
        }

        model.addAttribute("sampah", sampahList);
        model.addAttribute("filter", filter);
        return "admin/Storage/index";
    }
}

