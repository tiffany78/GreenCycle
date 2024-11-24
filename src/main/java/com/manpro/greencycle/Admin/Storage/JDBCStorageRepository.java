package com.manpro.greencycle.Admin.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCStorageRepository implements StorageRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Storage> findAll(){
        String sql = "SELECT * FROM storage_view";
        return jdbcTemplate.query(sql, this::mapRowToStorage);
    }

    private Storage mapRowToStorage(ResultSet resultSet, int rowNum) throws SQLException{
        return new Storage(
            resultSet.getString("sampah"), 
            resultSet.getInt("kapasitas"), 
            resultSet.getString("unit")
        );
    }

    public List<Storage> findWithFilter(String filter){
        String sql = "SELECT * FROM storage_view WHERE sampah ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToStorage, "%"+filter+"%");
    }
}
