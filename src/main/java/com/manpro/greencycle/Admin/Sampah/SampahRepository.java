package com.manpro.greencycle.Admin.Sampah;

import java.util.List;

public interface SampahRepository {
    List<Sampah> findAll();
    void tambahSampah(String nama, String unit, int harga);
    List<Sampah> getSampahById(int id);
}
