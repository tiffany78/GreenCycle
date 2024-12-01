package com.manpro.greencycle.Register;

import java.util.List;

public interface RegisterRepository {
    List<Kecamatan> getAllKec();
    List<Kelurahan> getAllKel(int id_kecamatan);
    void tambahUser(User user);
}
