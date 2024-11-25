package com.manpro.greencycle.Admin.SetoranMember;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/SetoranMember") // Update this to match the path used in your template
public class SetoranMemberController {
    @Autowired
    private final SetoranMemberRepository setoranMemberRepository;

    public SetoranMemberController(SetoranMemberRepository setoranMemberRepository) {
        this.setoranMemberRepository = setoranMemberRepository;
    }

    @GetMapping
    public String getSetoranMembers(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "tgl_awal", required = false) LocalDate tgl_awal,
            @RequestParam(value = "tgl_akhir", required = false) LocalDate tgl_akhir,
            Model model) {
        // Retrieve list of SetoranMember
        model.addAttribute("filter", filter);
        model.addAttribute("tgl_awal", tgl_awal);
        model.addAttribute("tgl_akhir", tgl_akhir);
        var setoranList = setoranMemberRepository.findAll(filter,tgl_awal,tgl_akhir);

        // Calculate total income (sum of all subtotal)
        double totalPendapatan = setoranList.stream()
                .mapToDouble(setoran -> Double.parseDouble(setoran.getSubtotal())) // assuming subtotal is stored as a
                                                                                   // String
                .sum();

        // Add data to model
        model.addAttribute("setoranList", setoranList); // Pass list of SetoranMember
        model.addAttribute("totalPendapatan", totalPendapatan); // Add total income

        return "admin/SetoranMember/index"; // Ensure this matches the template path
    }

    @GetMapping("/SetoranMember/details/{setoranId}")
    @ResponseBody
    public List<SetoranDetail> getSetoranDetails(@PathVariable int setoranId) {
        return setoranMemberRepository.getSetoranDetails(setoranId);
    }
    
    @GetMapping("/TambahSetoranMember")
    public String tambahSetoranMember() {
        return "admin/TambahSetoranMember/index";
    }
}
