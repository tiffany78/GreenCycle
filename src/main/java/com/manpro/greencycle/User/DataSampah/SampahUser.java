package com.manpro.greencycle.User.DataSampah;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SampahUser {
    private int id_sampah;
    private String nama;
    private String unit;
    private double harga;
    private Date tanggal_perubahan;
}
