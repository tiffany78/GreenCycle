package com.manpro.greencycle.Admin.SetoranMember;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    @GetMapping("/SetoranMember/details/{setoranId}/{tanggal}")
    @ResponseBody
    public List<SetoranDetail> getSetoranDetails(
        @PathVariable int setoranId,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggal)  {
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
        System.out.println(id_member);

        valueSetoran.forEach((key, value) -> {
            if(!key.equals("memberList")){
                int id_sampah = Integer.parseInt(key);
                if(!value.equals("")){
                    int kuantitas = Integer.parseInt(value);
                    if(kuantitas > 0){
                        setoranMemberRepository.addSetoranMember(id_member, id_sampah, kuantitas);
                    }
                }
            }
        });
        return "redirect:/admin/SetoranMember";
    }
}
