package com.manpro.greencycle.Admin.SetoranMember;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetoranMember {
    private Long id;
    private String nama;
    private String subtotal;
    private Date tanggal;
}
