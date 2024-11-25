package com.manpro.greencycle.Admin.TPA;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Storage {
    private Integer id_sampah;
    private String sampah;
    private String unit;
    private Integer kapasitas;
}
