package com.manpro.greencycle.Admin.SetoranMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.manpro.greencycle.Admin.Sampah.Sampah;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
                    GROUP BY sm.tgl_transaksi, p.nama, sm.id_member
                    ORDER BY sm.tgl_transaksi, p.nama;
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
    
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SetoranMember setoranMember = new SetoranMember();
            setoranMember.setId(rs.getLong("id_member"));
            setoranMember.setNama(rs.getString("member_name"));

            BigDecimal subtotal = rs.getBigDecimal("subtotal");
            setoranMember.setSubtotal(subtotal != null ? subtotal.toString() : "0.00");

            Date date = rs.getDate("tgl_transaksi");
            setoranMember.setTanggal(date);
            return setoranMember;
        });
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
    
            Date date = rs.getDate("tgl_transaksi");
            setoranMember.setTanggal(date);
            return setoranMember;
        }, "%" + filter + "%");
    }
    
    public List<Sampah> findSampahAll(){
        String sql = "SELECT * FROM sampah ORDER BY id_sampah";
        return jdbcTemplate.query(sql, this::mapRowToSampahList);
    }

    private Sampah mapRowToSampahList(ResultSet resultSet, int rowNum) throws SQLException{
        return new Sampah(
            resultSet.getInt("id_sampah"),
            resultSet.getString("nama"),
            resultSet.getString("unit"),
            resultSet.getDouble("harga"),
            resultSet.getDate("tanggal_perubahan")
        );
    }

    public List<Member> findMemberAll(){
        String sql = "SELECT id, nama FROM pengguna WHERE peran = 'member'";
        return jdbcTemplate.query(sql, this::mapRowToMember);
    }

    private Member mapRowToMember(ResultSet resultSet, int rowNum) throws SQLException{
        return new Member(
            resultSet.getInt("id"),
            resultSet.getString("nama")
        );
    }

    public void addSetoranMember(int id_member, int id_sampah, int kuantitas){
        LocalDate currDate = LocalDate.now();
        String sql = "INSERT into setoranmember (id_member, id_sampah, kuantitas_sampah, tgl_transaksi) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, id_member, id_sampah, kuantitas, currDate);

        sql = "UPDATE storage SET kapasitas = kapasitas + " + kuantitas + " WHERE id_sampah = " + id_sampah;
        jdbcTemplate.update(sql);
    }
}