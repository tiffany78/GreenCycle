package com.manpro.greencycle.Register;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String nama;
    private String email;
    private String username;
    private String password;
    private String no_telp;
    private String alamat;
    private int id_kelurahan; 
}
