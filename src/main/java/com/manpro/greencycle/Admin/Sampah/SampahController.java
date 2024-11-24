package com.manpro.greencycle.Admin.Sampah;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/")
public class SampahController {
    @Autowired
    private SampahRepository repo;

    @GetMapping("/DataSampah")
    public String dataSampah(@RequestParam(value = "filter", required = false) String filter, Model model) {
        List<Sampah> list = this.repo.findAll();

        if (filter != null && !filter.isEmpty()) {
            list = this.repo.filterSampah(filter);
            model.addAttribute("filter", filter);
        }

        model.addAttribute("sampah", list);
        return "admin/DataSampah/index";
    }

    @GetMapping("/TambahSampah")
    public String tambahSampah() {
        return "admin/TambahSampah/index";
    }

    @PostMapping("/TambahSampah")
    public String tambahSampah2(
        @RequestParam("nama-sampah") String nama,
        @RequestParam("unit-sampah") String unit,
        @RequestParam("harga-sampah") double harga){
            this.repo.tambahSampah(nama, unit, harga);
            return "redirect:/admin/DataSampah";
    }

    @GetMapping("/EditHarga")
    public String editHarga(@RequestParam(value="id", required=true) int idSampah, Model model) {
        List<Sampah> sampah = this.repo.getSampahById(idSampah);
        Sampah curr = sampah.get(0);  // Ambil sampah berdasarkan ID
        model.addAttribute("namaSampah", curr.getNama());
        model.addAttribute("unitSampah", curr.getUnit());
        model.addAttribute("id", curr.getId_sampah());
        return "admin/EditHarga/index";
    }

    @PostMapping("/EditHarga")
    public String editHarga2(
        @RequestParam("id") int idSampah,
        @RequestParam("harga-sampah") double hargaBaru) {
            this.repo.editHarga(idSampah, hargaBaru);
            return "redirect:/admin/DataSampah";
    }

    @GetMapping("/LandingPage")
    public String home(){
        return "admin/LandingPage/index";
    }
}
