package com.manpro.greencycle.Admin.Sampah;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sampah {
    private int id;
    private String nama;
    private String unit;
    private int harga;
    private Date tanggal;
}
