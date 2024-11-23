package com.manpro.greencycle.Admin.TPA;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSetoranTPARepository implements SetoranTPARepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SetoranTPA> getAllSetoran(String filter, Date tgl_awal, Date tgl_akhir) {
       String sql = "SELECT id, nama_tpa, tanggal FROM setoran WHERE 1=1";      //WHERE 1=1 untuk menambahkan "WHERE" jika ada filter nama/tanggal
        List<Object> params = new ArrayList<>();

        if (filter != null && !filter.isEmpty()) {
            sql += " AND nama_tpa ILIKE ?";
            params.add("%" + filter + "%");
        }
        if (tgl_awal != null) {
            sql += " AND tanggal >= ?";
            params.add(tgl_awal);
        }
        if (tgl_akhir != null) {
            sql += " AND tanggal <= ?";
            params.add(tgl_akhir);
        }
        return jdbcTemplate.query(sql, this::mapRowToSetoranTPA, params.toArray());
    }

    @Override
    public List<SampahDetail> getSetoranDetails(int setoranId) {
        String sql = "SELECT jenis_sampah, jumlah, satuan FROM setoran_details WHERE setoran_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSampahDetail, setoranId);
    }

    private SetoranTPA mapRowToSetoranTPA(ResultSet resultSet, int rowNum)throws SQLException {
        return new SetoranTPA(
            resultSet.getInt("id"),
            resultSet.getString("nama_tpa"),
            resultSet.getDate("tanggal")
        );
    }

    private SampahDetail mapRowToSampahDetail(ResultSet resultSet, int rowNum) throws SQLException {
        return new SampahDetail(
            resultSet.getString("jenis_sampah"),
            resultSet.getDouble("jumlah"),
            resultSet.getString("satuan")
        );
    }

    

}
