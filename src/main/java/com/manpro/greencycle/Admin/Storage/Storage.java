package com.manpro.greencycle.Admin.Storage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Storage {
    private String nama;
    private int kuantitas;
    private String unit;
}
