package com.manpro.greencycle.Admin.TPA;

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
@RequestMapping("/admin")
public class SetoranTPAController {
    @Autowired
    private SetoranTPARepository repo;

    @GetMapping("/SetoranTPA")
    public String setoranSampah(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "tgl_awal", required = false) LocalDate tgl_awal,
            @RequestParam(value = "tgl_akhir", required = false) LocalDate tgl_akhir,
            Model model) {

        model.addAttribute("filter", filter);
        model.addAttribute("tgl_awal", tgl_awal);
        model.addAttribute("tgl_akhir", tgl_akhir);
        model.addAttribute("setoranList", repo.getAllSetoran(filter, tgl_awal, tgl_akhir));
    
        return "admin/SetoranTPA/index";
    }

    @GetMapping("/SetoranTPA/details/{setoranId}")
    @ResponseBody
    public List<SampahDetail> getSetoranDetails(@PathVariable int setoranId) {
        return repo.getSetoranDetails(setoranId);
    }
}
