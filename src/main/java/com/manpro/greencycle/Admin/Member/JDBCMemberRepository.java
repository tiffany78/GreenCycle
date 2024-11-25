package com.manpro.greencycle.Admin.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCMemberRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Member> findAll(){
        String sql = "SELECT \n" + 
                        "\tid,\n" + 
                        "\tnama,\n" + 
                        "\tno_telp,\n" + 
                        "\talamat,\n" + 
                        "\tkelurahan,\n" + 
                        "\tkecamatan, \n" + 
                        "\tCOALESCE(COUNT(id_sampah), 0) AS jml_setoran\n" + 
                        "FROM\n" + 
                        "\tpengguna_view\n" + 
                        "GROUP BY id,nama,no_telp,alamat,kelurahan,kecamatan\n" + 
                        "ORDER BY id ASC";
        return jdbcTemplate.query(sql, this::mapRowToMember);
    }

    private Member mapRowToMember(ResultSet resultSet, int rowNum) throws SQLException{
        return new Member(
            resultSet.getInt("id"),
            resultSet.getString("nama"),
            resultSet.getString("no_telp"),
            resultSet.getString("alamat"),
            resultSet.getString("kelurahan"),
            resultSet.getString("kecamatan"),
            resultSet.getInt("jml_setoran")
        );
    }

    public List<Member> findWithFilter(String filter){
        String sql = "SELECT \n" + 
                        "\tid,\n" + 
                        "\tnama,\n" + 
                        "\tno_telp,\n" + 
                        "\talamat,\n" + 
                        "\tkelurahan,\n" + 
                        "\tkecamatan, \n" + 
                        "\tCOALESCE(COUNT(id_sampah), 0) AS jml_setoran\n" + 
                        "FROM\n" + 
                        "\tpengguna_view\n" +
                        "WHERE nama ILIKE ?\n" + 
                        "GROUP BY id,nama,no_telp,alamat,kelurahan,kecamatan\n" + 
                        "ORDER BY id ASC";
        return jdbcTemplate.query(sql, this::mapRowToMember, "%"+filter+"%");
    }
}
