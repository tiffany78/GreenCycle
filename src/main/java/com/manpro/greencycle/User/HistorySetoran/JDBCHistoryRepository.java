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

    public List<HistorySampah> findHistory(String username, String tglAwal, String tglAkhir) {
        String sql = """
                SELECT smv.sampah AS jenis_sampah,
                    SUM(smv.kuantitas) AS kuantitas_sampah,
                    SUM(smv.kuantitas * sp.harga) AS subtotal,
                    smv.tanggal AS tgl_transaksi,
                    smv.nama AS nama_member
                FROM
                    setoran_member_view smv
                JOIN
                    sampah sp ON smv.sampah = sp.nama  -- Menghubungkan dengan tabel sampah untuk harga
                WHERE
                    smv.nama = ?  -- Menggunakan nama member yang diteruskan dari session
                """;

        List<Object> params = new ArrayList<>();
        params.add(username);  // Menambahkan username ke query

        // Menambahkan filter tanggal jika tersedia
        if (tglAwal != null && !tglAwal.isEmpty()) {
            sql += " AND smv.tanggal >= ? ";
            params.add(tglAwal);
        }

        if (tglAkhir != null && !tglAkhir.isEmpty()) {
            sql += " AND smv.tanggal <= ? ";
            params.add(tglAkhir);
        }

        sql += "GROUP BY smv.sampah, smv.tanggal, smv.nama ORDER BY smv.tanggal DESC";

        return jdbcTemplate.query(sql, this::mapRow, params.toArray());
    }

    // Memetakan hasil query ke dalam objek HistorySampah
    public HistorySampah mapRow(ResultSet rs, int rowNum) throws SQLException {
        HistorySampah history = new HistorySampah();
        history.setJenisSampah(rs.getString("jenis_sampah"));
        history.setKuantitas(rs.getInt("kuantitas_sampah"));
        history.setSubtotal(rs.getLong("subtotal"));
        history.setTanggal(rs.getDate("tgl_transaksi").toLocalDate());
        return history;
    }
}
