package com.manpro.greencycle.User.DataSampah;

import java.util.List;

public interface SampahUserRepository {
    List<SampahUser> findAll();
    List<SampahUser> getSampahById(int id);
    List<SampahUser> filterSampah(String filter);
}
