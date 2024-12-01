package com.manpro.greencycle.Register;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRegister implements RegisterRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Kecamatan> getAllKec(){
        String sql = "SELECT * FROM kecamatan";
        return jdbcTemplate.query(sql, this::mapRowToKecamatan);
    }

    private Kecamatan mapRowToKecamatan(ResultSet resultSet, int rowNum) throws SQLException {
        return new Kecamatan(
            resultSet.getInt("id_kecamatan"),
            resultSet.getString("nama")
        );
    }

    @Override
    public List<Kelurahan> getAllKel(int id_kecamatan){
        String sql = "SELECT id_kelurahan, nama FROM kelurahan WHERE id_kecamatan = ?";
        return jdbcTemplate.query(sql, this::mapRotToKelurahan, id_kecamatan);
    }

    private Kelurahan mapRotToKelurahan(ResultSet resultSet, int rowNum) throws SQLException {
        return new Kelurahan(
            resultSet.getInt("id_kelurahan"),
            resultSet.getString("nama")
        );
    }

    @Override
    public void tambahUser(User user){
        String nama = user.getNama();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        String peran = "member";
        String notelp = user.getNo_telp();
        String alamat = user.getAlamat();
        int id_kel = user.getId_kelurahan();

        String sql = "INSERT INTO pengguna (nama, email, username, password, peran, no_telp, alamat, id_kelurahan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, nama, email, username, password, peran, notelp, alamat, id_kel);
    }
}
