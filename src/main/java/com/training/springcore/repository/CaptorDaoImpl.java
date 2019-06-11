package com.training.springcore.repository;

import com.training.springcore.model.Captor;
import com.training.springcore.model.Site;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class CaptorDaoImpl implements CaptorDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public CaptorDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static String SELECT_WITH_JOIN =
            "SELECT c.id, c.name, c.site_id, s.name as site_name " +
                    "FROM CAPTOR c inner join Site s on c.site_id = s.id ";

    @Override
    public List<Captor> findBySiteId(String siteId) {
        try{
            return jdbcTemplate.query(SELECT_WITH_JOIN + "where c.site_id = :site_id",
                    new MapSqlParameterSource("site_id", siteId),
                    this::captorMapper);
        }catch (InvalidDataAccessApiUsageException e) {return null;}

    }

    @Override
    public void create(Captor element) {
        jdbcTemplate.update("insert into CAPTOR (id, name, site_id) values (:id, :name, :site_id)",
                new MapSqlParameterSource()
                        .addValue("id", element.getId())
                        .addValue("name", element.getName())
                        .addValue("site_id" , element.getSite().getId()));
    }

    @Override
    public Captor findById(String s) {
        try {
            return jdbcTemplate.queryForObject(SELECT_WITH_JOIN +" where c.id = :id ",
                    new MapSqlParameterSource("id", s),
                    this::captorMapper);
        } catch (EmptyResultDataAccessException e) {return null;}

    }

    @Override
    public List<Captor> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOIN , this::captorMapper);
    }

    @Override
    public void update(Captor element) {
        jdbcTemplate.update("update CAPTOR set name = :name , site_id = :site_id where id = :id" ,
                new MapSqlParameterSource().addValue("name" , element.getName())
                                            .addValue("site_id" , element.getSite().getId())
                                            .addValue("id" , element.getId()));
    }

    @Override
    public void deleteById(String s) {
        jdbcTemplate.update("delete from CAPTOR where id = :id" ,
                new MapSqlParameterSource("id" , s)) ;
    }

    private Captor captorMapper(ResultSet rs, int rowNum) throws SQLException {
        Site site = new Site(rs.getString("site_name"));
        site.setId(rs.getString("site_id"));
        Captor captor = new Captor(rs.getString("name"), site);
        captor.setId(rs.getString("id"));
        return captor;
    }
}
