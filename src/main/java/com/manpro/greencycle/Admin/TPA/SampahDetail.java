package com.manpro.greencycle.Admin.TPA;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SampahDetail {
    private String jenis_sampah;  
    private double jumlah; 
    private String satuan;
}
