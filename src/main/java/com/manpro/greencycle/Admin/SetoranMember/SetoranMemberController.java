package com.manpro.greencycle.Admin.SetoranMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/SetoranMember")  // Update this to match the path used in your template
public class SetoranMemberController {

    private final SetoranMemberRepository setoranMemberRepository;

    @Autowired
    public SetoranMemberController(SetoranMemberRepository setoranMemberRepository) {
        this.setoranMemberRepository = setoranMemberRepository;
    }

    @GetMapping
    public String getSetoranMembers(Model model) {
        // Retrieve list of SetoranMember
        var setoranList = setoranMemberRepository.findAll();  
        
        // Calculate total income (sum of all subtotal)
        double totalPendapatan = setoranList.stream()
            .mapToDouble(setoran -> Double.parseDouble(setoran.getSubtotal())) // assuming subtotal is stored as a String
            .sum();

        // Add data to model
        model.addAttribute("setoranList", setoranList);  // Pass list of SetoranMember
        model.addAttribute("totalPendapatan", totalPendapatan);  // Add total income

        return "admin/SetoranMember/index"; // Ensure this matches the template path
    }
}

