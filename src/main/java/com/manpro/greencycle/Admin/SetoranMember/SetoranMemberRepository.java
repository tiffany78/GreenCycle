package com.manpro.greencycle.Admin.SetoranMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SetoranMemberRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public SetoranMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SetoranMember> findAll(String filter, LocalDate tgl_awal, LocalDate tgl_akhir) {
        String sql = """
                    SELECT
                        p.nama AS member_name,
                        sm.id_member,
                        SUM(sm.kuantitas_sampah * s.harga) AS subtotal,
                        sm.tgl_transaksi
                    FROM SetoranMember AS sm
                    JOIN Pengguna p ON sm.id_member = p.id
                    JOIN Sampah s ON sm.id_sampah = s.id_sampah
                    WHERE 1=1
                """;
        List<Object> params = new ArrayList<>();
        if (filter != null && !filter.isEmpty()) {
            sql += " AND p.nama ILIKE ? ";
            params.add("%" + filter + "%");
        }
        if (tgl_awal != null) {
            sql += " AND sm.tgl_transaksi >= ? ";
            params.add(tgl_awal);
        }
        if (tgl_akhir != null) {
            sql += " AND sm.tgl_transaksi <= ? ";
            params.add(tgl_akhir);
        }
        sql += " GROUP BY p.nama, sm.id_member, sm.tgl_transaksi ";
        sql += " ORDER BY p.nama, sm.tgl_transaksi ";
    
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SetoranMember setoranMember = new SetoranMember();
            setoranMember.setId(rs.getLong("id_member"));
            setoranMember.setNama(rs.getString("member_name"));
    
            BigDecimal subtotal = rs.getBigDecimal("subtotal");
            setoranMember.setSubtotal(subtotal != null ? subtotal.toString() : "0.00");
    
            setoranMember.setTanggal(rs.getTimestamp("tgl_transaksi") != null ? 
                rs.getTimestamp("tgl_transaksi").toLocalDateTime() : null);
            return setoranMember;
        }, params.isEmpty() ? new Object[]{} : params.toArray());
    }
    

    public List<SetoranDetail> getSetoranDetails(int setoranId) {
        String sql = "SELECT sampah, kuantitas, unit FROM setoran_member_view WHERE id_member = ?";
        return jdbcTemplate.query(sql, this::mapRowToSampahDetail, setoranId);
    }

    private SetoranDetail mapRowToSampahDetail(ResultSet resultSet, int rowNum) throws SQLException {
        return new SetoranDetail(
                resultSet.getString("sampah"),
                resultSet.getInt("kuantitas"),
                resultSet.getString("unit"));
    }

    public List<SetoranMember> findByName(String filter) {
        String sql = """
                    SELECT
                        p.nama AS member_name,
                        sm.id_member,
                        SUM(sm.kuantitas_sampah * s.harga) AS subtotal,
                        sm.tgl_transaksi
                    FROM SetoranMember sm
                    JOIN Pengguna p ON sm.id_member = p.id
                    JOIN Sampah s ON sm.id_sampah = s.id_sampah
                    WHERE p.nama ILIKE ?  -- Menambahkan filter pencarian berdasarkan nama member
                    GROUP BY p.nama, sm.id_member, sm.tgl_transaksi
                    ORDER BY p.nama, sm.tgl_transaksi;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SetoranMember setoranMember = new SetoranMember();
            setoranMember.setId(rs.getLong("id_member"));
            setoranMember.setNama(rs.getString("member_name"));

            BigDecimal subtotal = rs.getBigDecimal("subtotal");
            setoranMember.setSubtotal(subtotal != null ? subtotal.toString() : "0.00");

            setoranMember.setTanggal(rs.getTimestamp("tgl_transaksi").toLocalDateTime());
            return setoranMember;
        }, "%" + filter + "%");
    }

}