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
        String sql = "SELECT * FROM sampah";
        return jdbcTemplate.query(sql, this::mapRowToSampahList);
    }

    private Sampah mapRowToSampahList(ResultSet resultSet, int rowNum) throws SQLException{
        return new Sampah(
            resultSet.getInt("id"),
            resultSet.getString("nama"),
            resultSet.getString("unit"),
            resultSet.getInt("harga"),
            resultSet.getDate("tanggal")
        );
    }

    @Override
    public void tambahSampah(String nama, String unit, int harga){
        LocalDate currDate = LocalDate.now();

        String sql = "INSERT INTO sampah (nama, unit, harga, tanggal) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, nama, unit, harga, currDate);
    }

    @Override
    public List<Sampah> getSampahById(int id){
        String sql = "SELECT * FROM sampah WHERE id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSampahList, id);
    }
}
