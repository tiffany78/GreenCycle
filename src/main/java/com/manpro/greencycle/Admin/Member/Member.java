package com.manpro.greencycle.Admin.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
    private int id;
    private String nama;
    private String noTelp;
    private String alamat;
    private String kelurahan;
    private String kecamatan;
    private int jml_setoran;
}
