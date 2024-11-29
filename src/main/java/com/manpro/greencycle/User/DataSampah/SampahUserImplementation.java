package com.manpro.greencycle.User.DataSampah;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SampahUserImplementation implements SampahUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SampahUser> findAll() {
        String sql = "SELECT * FROM sampah ORDER BY id_sampah";
        return jdbcTemplate.query(sql, this::mapRowToSampahList);
    }

    public List<SampahUser> filterSampah(String filter) {
        String sql = "SELECT * FROM sampah WHERE nama ILIKE '%" + filter + "%' ORDER BY id_sampah";
        return jdbcTemplate.query(sql, this::mapRowToSampahList);
    }

    private SampahUser mapRowToSampahList(ResultSet resultSet, int rowNum) throws SQLException {
        return new SampahUser(
                resultSet.getInt("id_sampah"),
                resultSet.getString("nama"),
                resultSet.getString("unit"),
                resultSet.getDouble("harga"),
                resultSet.getDate("tanggal_perubahan"));
    }

    @Override
    public List<SampahUser> getSampahById(int id) {
        String sql = "SELECT * FROM sampah WHERE id_sampah = ?";
        return jdbcTemplate.query(sql, this::mapRowToSampahList, id);
    }
}
