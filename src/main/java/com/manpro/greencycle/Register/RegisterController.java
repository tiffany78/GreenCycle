package com.manpro.greencycle.Register;

import java.util.List;

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
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterRepository repo;
    private User user;

    @GetMapping
    public String register(){
        return "user/Register/index1";
    }

    @PostMapping
    public String newUser(
        @RequestParam("nama") String nama,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("nohp") String nohp,
        Model model
    ) {
        this.user = new User(nama, email, nama.trim().toLowerCase(), password, nohp, null, 0);
        System.out.println(this.user);
        return "redirect:/register/page2";
    }

    @GetMapping("/page2")
    public String register2(Model model){
        List<Kecamatan> list = this.repo.getAllKec();
        model.addAttribute("kecamatanList", list);
        return "user/Register/index2";
    }

    @GetMapping("/kelurahan/{idKecamatan}")
    @ResponseBody
    public List<Kelurahan> getKelurahan(@PathVariable("idKecamatan") int idKecamatan) {
        return repo.getAllKel(idKecamatan);
    }

    @PostMapping("/submitForm")
    public String submitForm(
        @RequestParam("alamat") String alamat,
        @RequestParam("kelurahanList") int id_kelurahan
    ) {
        this.user.setAlamat(alamat);
        this.user.setId_kelurahan(id_kelurahan);
        System.out.println(id_kelurahan);
        this.repo.tambahUser(this.user);
        return "redirect:/loginUser";
    }
}
