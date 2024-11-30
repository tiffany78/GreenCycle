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
}
