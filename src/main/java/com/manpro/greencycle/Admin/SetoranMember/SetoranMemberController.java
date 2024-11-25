package com.manpro.greencycle.Admin.SetoranMember;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String getSetoranMembers(Model model) {
        // Retrieve list of SetoranMember
        var setoranList = setoranMemberRepository.findAll();

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
    // @GetMapping("/admin/SetoranMember")
    // public String getSetoranMember(@RequestParam(value = "filter", defaultValue =
    // "") String filter, Model model) {
    // List<SetoranMember> setoranList;

    // if (filter.isEmpty()) {
    // // Jika tidak ada filter, ambil semua data
    // setoranList = setoranMemberRepository.findAll();
    // } else {
    // // Jika ada filter, cari berdasarkan nama member
    // setoranList = setoranMemberRepository.findByName(filter);
    // }

    // model.addAttribute("setoranList", setoranList);
    // return "setoran_member_view"; // Nama view yang sesuai
    // }
    @GetMapping("/TambahSetoranMember")
    public String tambahSetoranMember() {
        return "admin/TambahSetoranMember/index";
    }
}
