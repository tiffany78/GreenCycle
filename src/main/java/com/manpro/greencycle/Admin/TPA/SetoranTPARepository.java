package com.manpro.greencycle.Admin.TPA;

import java.time.LocalDate;
import java.util.List;

public interface SetoranTPARepository {
    List<SetoranTPA> getAllSetoran(String filter, LocalDate tgl_awal, LocalDate tgl_akhir);
    List<SampahDetail> getSetoranDetails(int setoranId);
    
}
