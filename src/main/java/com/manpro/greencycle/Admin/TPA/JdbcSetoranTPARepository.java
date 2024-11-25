package com.manpro.greencycle.Admin.TPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public List<SetoranTPA> getAllSetoran(String filter, LocalDate tgl_awal, LocalDate tgl_akhir) {
        String sql = "SELECT id, tpa, sampah, kuantitas, unit, tanggal FROM setoran_pusat_view WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (filter != null && !filter.isEmpty()) {
            sql += " AND tpa ILIKE ?";
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
        String sql = "SELECT sampah, kuantitas, unit FROM setoran_pusat_view WHERE id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSampahDetail, setoranId);
    }

    @Override
    public List<SampahDetail> getAllRekapanSetoran(String filter, LocalDate tgl_awal, LocalDate tgl_akhir) {
        String sql = "SELECT sampah, unit, SUM(kuantitas) AS sum_kuantitas FROM setoran_pusat_view WHERE 1=1";
        List<Object> params = new ArrayList<>();
    
        if (filter != null && !filter.isEmpty()) {
            sql += " AND sampah ILIKE ?";
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
        
        // Fix GROUP BY
        sql += " GROUP BY sampah, unit";
    
        return jdbcTemplate.query(sql, this::mapRowToRekapanSetoran, params.toArray());
    }
    
    private SampahDetail mapRowToRekapanSetoran(ResultSet resultSet, int rowNum)throws SQLException {
        return new SampahDetail(
            resultSet.getString("sampah"),
            resultSet.getInt("sum_kuantitas"),
            resultSet.getString("unit")
        );
    }

    private SetoranTPA mapRowToSetoranTPA(ResultSet resultSet, int rowNum)throws SQLException {
        return new SetoranTPA(
            resultSet.getInt("id"),
            resultSet.getString("tpa"),
            resultSet.getDate("tanggal")
        );
    }

    private SampahDetail mapRowToSampahDetail(ResultSet resultSet, int rowNum) throws SQLException {
        return new SampahDetail(
            resultSet.getString("sampah"),
            resultSet.getInt("kuantitas"),
            resultSet.getString("unit")
        );
    }

    

}
