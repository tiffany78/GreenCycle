package com.manpro.greencycle.User.HistorySetoran;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorySampah {
    private String jenisSampah;
    private int kuantitas;
    private String subtotal;
    private Date tanggal;
}