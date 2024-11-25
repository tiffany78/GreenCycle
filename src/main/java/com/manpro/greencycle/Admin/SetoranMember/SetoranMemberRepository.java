package com.manpro.greencycle.Admin.SetoranMember;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class SetoranMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public SetoranMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SetoranMember> findAll() {
    String sql = """
        SELECT 
            sm.id_transaksi,
            p.nama AS member_name,
            SUM(sm.kuantitas_sampah * s.harga) AS subtotal,  -- Menghitung subtotal berdasarkan harga sampah dan kuantitas
            sm.tgl_transaksi
        FROM SetoranMember sm
        JOIN Pengguna p ON sm.id_member = p.id
        JOIN Sampah s ON sm.id_sampah = s.id_sampah
        GROUP BY sm.id_transaksi, p.nama, sm.tgl_transaksi;
    """;

    return jdbcTemplate.query(sql, (rs, rowNum) -> {
        SetoranMember setoranMember = new SetoranMember();
        setoranMember.setId(rs.getLong("id_transaksi"));
        setoranMember.setNama(rs.getString("member_name"));
        
        // Menyimpan subtotal sebagai BigDecimal
        BigDecimal subtotal = rs.getBigDecimal("subtotal");
        setoranMember.setSubtotal(subtotal != null ? subtotal.toString() : "0.00");  // Convert ke string jika diperlukan
        
        setoranMember.setTanggal(rs.getTimestamp("tgl_transaksi").toLocalDateTime());
        return setoranMember;
    });
}

}