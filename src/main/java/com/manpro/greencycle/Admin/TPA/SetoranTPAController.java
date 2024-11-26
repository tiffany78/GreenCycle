package com.manpro.greencycle.Admin.TPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/RekapanSetoran")
    public String rekapanSetoran(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "tgl_awal", required = false) LocalDate tgl_awal,
            @RequestParam(value = "tgl_akhir", required = false) LocalDate tgl_akhir,
            Model model) {

        model.addAttribute("filter", filter);
        model.addAttribute("tgl_awal", tgl_awal);
        model.addAttribute("tgl_akhir", tgl_akhir);
        model.addAttribute("rekapanSetoranList", repo.getAllRekapanSetoran(filter, tgl_awal, tgl_akhir));
        
        return "admin/RekapanSetoran/index";
    }

    @GetMapping("/TambahSetoranTPA")
    public String tambahSetoranTPA(Model model) {
        List<TPA> list = this.repo.getAllTPA();
        model.addAttribute("tpaList", list);

        List<Storage> list2 = this.repo.getAllStorage();
        model.addAttribute("storage", list2);
        System.out.println(list2);

        return "admin/TambahSetoranTPA/index";
    }

    @PostMapping("/TambahSetoranTPA3")
    public String tambahSetoranTPA3(@RequestParam Map<String, String> valueSetoran, Model model) {
        String tpaValue = valueSetoran.get("tpaList");
        int id_tpa = Integer.parseInt(tpaValue);
        System.out.println(id_tpa);

        valueSetoran.forEach((key, value) -> {
            if(!key.equals("tpaList")){
                int idSampah = key.charAt(13) - '0';
                if(!value.equals("")){
                    int setoran = Integer.parseInt(value);
                    if(setoran > 0){
                        this.repo.addSetoranTPA(id_tpa, idSampah, setoran);
                    }
                }
            }
        });

        // Lakukan logika lain seperti penyimpanan ke database
        return "redirect:/admin/SetoranTPA";
    }

}
