package com.manpro.greencycle.Admin.TPA;

import java.time.LocalDate;
import java.util.List;

public interface SetoranTPARepository {
    List<SetoranTPA> getAllSetoran(String filter, LocalDate tgl_awal, LocalDate tgl_akhir);
    List<SampahDetail> getSetoranDetails(int setoranId);
    List<TPA> getAllTPA();
    List<Storage> getAllStorage();
    void addSetoranTPA(int id_tpa, int id_sampah, int kuantitas);
}
