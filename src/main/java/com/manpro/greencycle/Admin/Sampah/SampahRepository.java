package com.manpro.greencycle.Admin.Sampah;

import java.util.List;

public interface SampahRepository {
    List<Sampah> findAll();
    void tambahSampah(String nama, String unit, double harga);
    List<Sampah> getSampahById(int id);
    void editHarga(int id_sampah, double hargaBaru);
    List<Sampah> filterSampah(String filter);
}
