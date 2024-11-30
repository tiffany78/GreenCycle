package com.manpro.greencycle.User.HistorySetoran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class HistorySetoranController {

    @Autowired
    private JDBCHistoryRepository historySampahRepository;  // Ubah ke repository yang benar

    @GetMapping("/HistorySetoran")
    public String showHistorySetoran(Model model, HttpSession session, 
                                      @RequestParam(required = false) String filter, 
                                      @RequestParam(required = false) String tgl_awal, 
                                      @RequestParam(required = false) String tgl_akhir) {
        // pastikan session username ada
        String username = (String) session.getAttribute("username");
        if (username != null) {
            // Ambil data history berdasarkan filter dan tanggal
            List<HistorySampah> history = historySampahRepository.findHistory(username, tgl_awal, tgl_akhir);
            model.addAttribute("setoranList", history);  // Sesuaikan dengan nama variabel yang digunakan di HTML
            model.addAttribute("filter", filter);  // Kirimkan filter untuk ditampilkan di input field
            model.addAttribute("tgl_awal", tgl_awal);  // Kirimkan tanggal awal
            model.addAttribute("tgl_akhir", tgl_akhir);  // Kirimkan tanggal akhir
        }
        return "user/HistorySetoran/index";  // Nama tampilan yang benar
    }
}
