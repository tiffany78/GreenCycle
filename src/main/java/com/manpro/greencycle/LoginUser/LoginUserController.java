package com.manpro.greencycle.LoginUser;

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
@RequestMapping("/loginUser")
public class LoginUserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String login(){
        return "LoginUser/index";
    }

    public List<LoginUserPengguna> findPengguna(String email) {
        String sql = "SELECT * FROM login_view WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapToPengguna, email);
    }    

    private LoginUserPengguna mapToPengguna(ResultSet resultSet, int rowNum) throws SQLException{
        return new LoginUserPengguna(
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

        List<LoginUserPengguna> pengguna = findPengguna(email);

        if (pengguna.isEmpty()) {
            return "LoginUser/index";
        } else {
            String realPassword = pengguna.get(0).getPassword();
            if (realPassword.equals(password) && pengguna.get(0).getPeran().equals("member")) {
                // Simpan nama pengguna dalam session
                session.setAttribute("username", pengguna.get(0).getNama());
                return "redirect:/user";
            } else {
                return "LoginUser/index";
            }
        }
    }
}
