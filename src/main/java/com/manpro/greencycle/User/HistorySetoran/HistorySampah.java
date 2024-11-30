package com.manpro.greencycle.User.HistorySetoran;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorySampah {
    private String jenisSampah;
    private int kuantitas;
    private long subtotal;
    private LocalDate tanggal;
}