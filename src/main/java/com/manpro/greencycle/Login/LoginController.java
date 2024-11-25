package com.manpro.greencycle.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String login(){
        return "Login/index";
    }

    public List<LoginPengguna> findPengguna(String email) {
        String sql = "SELECT * FROM login_view WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapToPengguna, email);
    }    

    private LoginPengguna mapToPengguna(ResultSet resultSet, int rowNum) throws SQLException{
        return new LoginPengguna(
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("peran"),
            resultSet.getString("nama")
        );
    }

    @PostMapping
    public String validation(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        Model model, HttpSession session) {

        List<LoginPengguna> pengguna = findPengguna(email);

        if (pengguna.isEmpty()) {
            return "Login/index";
        } else {
            String realPassword = pengguna.get(0).getPassword();
            if (realPassword.equals(password) && pengguna.get(0).getPeran().equals("admin")) {
                // Simpan nama pengguna dalam session
                session.setAttribute("username", pengguna.get(0).getNama());
                return "redirect:/admin/LandingPage";
            } else {
                return "Login/index";
            }
        }
    }

}
