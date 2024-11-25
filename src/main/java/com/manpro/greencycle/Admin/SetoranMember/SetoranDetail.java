package com.manpro.greencycle.Admin.SetoranMember;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SetoranDetail{
    private String jenis_sampah;  
    private double jumlah; 
    private String satuan;
}
