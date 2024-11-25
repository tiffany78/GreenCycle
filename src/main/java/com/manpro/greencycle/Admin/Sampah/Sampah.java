package com.manpro.greencycle.Admin.Sampah;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sampah {
    private int id_sampah;
    private String nama;
    private String unit;
    private double harga;
    private Date tanggal_perubahan;
}
