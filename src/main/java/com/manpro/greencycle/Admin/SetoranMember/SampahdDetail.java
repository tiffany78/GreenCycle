package com.manpro.greencycle.Admin.SetoranMember;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampahdDetail {
    private String namaSampah;
    private int kuantitas;
    private BigDecimal harga;
    private BigDecimal totalHarga;
}
