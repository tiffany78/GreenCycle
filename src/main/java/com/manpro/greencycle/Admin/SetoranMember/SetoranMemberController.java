package com.manpro.greencycle.Admin.SetoranMember;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manpro.greencycle.Admin.Sampah.Sampah;

@Controller
@RequestMapping("/admin/SetoranMember")
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
        
        model.addAttribute("filter", filter);
        model.addAttribute("tgl_awal", tgl_awal);
        model.addAttribute("tgl_akhir", tgl_akhir);
        var setoranList = setoranMemberRepository.findAll(filter,tgl_awal,tgl_akhir);

        double totalPendapatan = setoranList.stream()
                .mapToDouble(setoran -> Double.parseDouble(setoran.getSubtotal())).sum();

        model.addAttribute("setoranList", setoranList); 
        model.addAttribute("totalPendapatan", totalPendapatan); 

        return "admin/SetoranMember/index"; 
    }

    @GetMapping("/SetoranMember/details/{setoranId}/{tanggal}")
    @ResponseBody
    public List<SetoranDetail> getSetoranDetails(
            @PathVariable int setoranId,
            @PathVariable LocalDate tanggal) {
        return setoranMemberRepository.getSetoranDetails(setoranId, tanggal);
    }

    
    @GetMapping("/TambahSetoranMember")
    public String tambahSetoranMember(Model model) {
        List<Member> list = setoranMemberRepository.findMemberAll();
        model.addAttribute("memberList", list);

        List<Sampah> list2 = setoranMemberRepository.findSampahAll();
        model.addAttribute("sampahList", list2);
        return "admin/TambahSetoranMember/index";
    }

    @PostMapping("/TambahSetoranMember")
    public String tambahSetoranMember(@RequestParam Map<String, String> valueSetoran, Model model) {
    String tpaValue = valueSetoran.get("memberList");
    int id_member = Integer.parseInt(tpaValue);

    // Variabel untuk menyimpan error
    boolean hasError = false;
    StringBuilder errorMessage = new StringBuilder("Nilai setoran tidak boleh negatif untuk: ");

    for (Map.Entry<String, String> entry : valueSetoran.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();

        if (!key.equals("memberList") && !value.isEmpty()) {
            int id_sampah = Integer.parseInt(key);
            int kuantitas = Integer.parseInt(value);

            if (kuantitas < 0) {
                hasError = true;
                errorMessage.append("ID Sampah ").append(id_sampah).append(" ");
            }
        }
    }

    if (hasError) {
        // Jika ada error, tambahkan pesan error ke model dan kembali ke halaman tambah setoran
        model.addAttribute("errorMessage", errorMessage.toString());
        List<Member> list = setoranMemberRepository.findMemberAll();
        model.addAttribute("memberList", list);

        List<Sampah> list2 = setoranMemberRepository.findSampahAll();
        model.addAttribute("sampahList", list2);

        return "admin/TambahSetoranMember/index";
    }

    // Jika tidak ada error, lakukan penyimpanan data ke database
    valueSetoran.forEach((key, value) -> {
        if (!key.equals("memberList") && !value.equals("")) {
            int id_sampah = Integer.parseInt(key);
            int kuantitas = Integer.parseInt(value);
            if (kuantitas > 0) {
                setoranMemberRepository.addSetoranMember(id_member, id_sampah, kuantitas);
            }
        }
    });

    return "redirect:/admin/SetoranMember";
    }
}
