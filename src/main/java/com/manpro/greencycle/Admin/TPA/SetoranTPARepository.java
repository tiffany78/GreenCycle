package com.manpro.greencycle.Admin.TPA;

import java.sql.Date;
import java.util.List;

public interface SetoranTPARepository {
    List<SetoranTPA> getAllSetoran(String filter, Date tgl_awal, Date tgl_akhir);
    List<SampahDetail> getSetoranDetails(int setoranId);
    
}
