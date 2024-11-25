package com.manpro.greencycle.Admin.Sampah;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SampahImplementation implements SampahRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Sampah> findAll(){
        String sql = "SELECT * FROM sampah ORDER BY id_sampah";
        return jdbcTemplate.query(sql, this::mapRowToSampahList);
    }

    public List<Sampah> filterSampah(String filter){
        String sql = "SELECT * FROM sampah WHERE nama ILIKE '%" + filter + "%' ORDER BY id_sampah";
        return jdbcTemplate.query(sql, this::mapRowToSampahList);
    }

    private Sampah mapRowToSampahList(ResultSet resultSet, int rowNum) throws SQLException{
        return new Sampah(
            resultSet.getInt("id_sampah"),
            resultSet.getString("nama"),
            resultSet.getString("unit"),
            resultSet.getDouble("harga"),
            resultSet.getDate("tanggal_perubahan")
        );
    }

    @Override
    public void tambahSampah(String nama, String unit, double harga){
        LocalDate currDate = LocalDate.now();

        String sql = "INSERT INTO sampah (nama, unit, harga, tanggal_perubahan) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, nama, unit, harga, currDate);

        List<Sampah> list = filterSampah(nama);
        int id_sampah = list.get(0).getId_sampah();
        sql = "INSERT INTO storage VALUES (?, ?)";
        jdbcTemplate.update(sql, id_sampah, 0);
    }

    @Override
    public List<Sampah> getSampahById(int id){
        String sql = "SELECT * FROM sampah WHERE id_sampah = ?";
        return jdbcTemplate.query(sql, this::mapRowToSampahList, id);
    }

    @Override
    public void editHarga(int id_sampah, double hargaBaru){
        LocalDate currDate = LocalDate.now();
        String sql = "UPDATE sampah SET harga = ?, tanggal_perubahan = ? WHERE id_sampah = ?";
        jdbcTemplate.update(sql, hargaBaru, currDate, id_sampah);
    }
}
