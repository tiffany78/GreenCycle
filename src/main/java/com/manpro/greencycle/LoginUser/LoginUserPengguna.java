package com.manpro.greencycle.LoginUser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserPengguna {
    private String email;
    private String password;
    private String peran;
    private String nama;
}
