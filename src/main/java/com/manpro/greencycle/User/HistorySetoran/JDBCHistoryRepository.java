package com.manpro.greencycle.User.HistorySetoran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<HistorySampah> findHistory(String username, LocalDate tgl_awal, LocalDate tgl_akhir, String filter) {
        String sql = """
                SELECT smv.sampah AS jenis_sampah,
                       SUM(smv.kuantitas) AS total_kuantitas,
                       SUM(smv.kuantitas * sp.harga) AS total_harga,
                       smv.tanggal AS tanggal
                FROM setoran_member_view smv
                JOIN sampah sp ON smv.sampah = sp.nama
                WHERE smv.nama ILIKE ?
        """;
    
        List<Object> params = new ArrayList<>();
        params.add("%" + username + "%");  // Username with LIKE for partial match
    
        // Add date filters if provided
        if (tgl_awal != null ) {
            sql += " AND smv.tanggal >= ? ";
            params.add(tgl_awal);
        }
    
        if (tgl_akhir != null ) {
            sql += " AND smv.tanggal <= ? ";
            params.add(tgl_akhir);
        }
    
        // Add filter for sampah type if provided
        if (filter != null && !filter.isEmpty()) {
            sql += " AND smv.sampah ILIKE ? ";
            params.add("%" + filter + "%");
        }
    
        sql += "GROUP BY smv.sampah, smv.tanggal ORDER BY smv.tanggal";
    
        // Execute the query with the provided parameters
        return jdbcTemplate.query(sql, this::mapRow, params.toArray());
    }
    

    // Mapping the result set to HistorySampah object
    private HistorySampah mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HistorySampah(
        rs.getString("jenis_sampah"),
        rs.getInt("total_kuantitas"),
        rs.getString("total_harga"),
        rs.getDate("tanggal")
        );
    }
}
