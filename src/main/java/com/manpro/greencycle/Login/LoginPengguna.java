package com.manpro.greencycle.Login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginPengguna {
    private String email;
    private String password;
    private String peran;
    private String nama;
}
