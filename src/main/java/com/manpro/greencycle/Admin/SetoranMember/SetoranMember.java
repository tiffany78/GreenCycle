package com.manpro.greencycle.Admin.SetoranMember;

import java.time.LocalDateTime;

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
    private LocalDateTime tanggal;
}
