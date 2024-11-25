package com.manpro.greencycle.Admin.SetoranMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SetoranMemberRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public SetoranMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SetoranMember> findAll() {
        String sql = """
                    SELECT
                        p.nama AS member_name,
                        sm.id_member,
                        SUM(sm.kuantitas_sampah * s.harga) AS subtotal,  -- Menghitung subtotal berdasarkan harga sampah dan kuantitas
                        sm.tgl_transaksi
                    FROM SetoranMember sm
                    JOIN Pengguna p ON sm.id_member = p.id
                    JOIN Sampah s ON sm.id_sampah = s.id_sampah
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
        });
    }

    public List<SetoranDetail> getSetoranDetails(int setoranId) {
        String sql = "SELECT sampah, kuantitas, unit FROM setoran_member_view WHERE id = ?";
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
                    WHERE p.nama LIKE ?  -- Menambahkan filter pencarian berdasarkan nama member
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