package com.manpro.greencycle.Admin.TPA;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SetoranTPA {
    private int id;             
    private String namaTPA;     
    private Date tanggal;   
}
