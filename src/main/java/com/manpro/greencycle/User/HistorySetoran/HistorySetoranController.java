package com.manpro.greencycle.User.HistorySetoran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class HistorySetoranController {

    @Autowired
    private JDBCHistoryRepository historySampahRepository;

    @GetMapping("/HistorySetoran")
    public String showHistorySetoran(Model model, HttpSession session,
                                     @RequestParam(required = false) String filter,
                                     @RequestParam(required = false) LocalDate tgl_awal,
                                     @RequestParam(required = false) LocalDate tgl_akhir) {

        String username = (String) session.getAttribute("username");
        if (username != null) {
            List<HistorySampah> history = historySampahRepository.findHistory(username, tgl_awal, tgl_akhir, filter);
            double totalPendapatan = history.stream()
                                            .mapToDouble(h -> Double.parseDouble(h.getSubtotal()))
                                            .sum();
            model.addAttribute("filter", filter);
            model.addAttribute("tgl_awal", tgl_awal);
            model.addAttribute("tgl_akhir", tgl_akhir);
            model.addAttribute("setoranList", history);
            model.addAttribute("totalPendapatan", totalPendapatan);
        }
        return "user/HistorySetoran/index";
    }
}
