package com.training.springcore.repository;

import com.training.springcore.model.Site;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class SiteDaoImpl implements SiteDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public SiteDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Site element) {
        jdbcTemplate.update("insert into SITE (id, name) values (:id, :name)",
                new MapSqlParameterSource()
                        .addValue("id", element.getId())
                        .addValue("name", element.getName()));
    }

    @Override
    public Site findById(String s) {
        try{
            return jdbcTemplate.queryForObject("select id, name from SITE where id = :id ",
                    new MapSqlParameterSource("id", s),
                    this::siteMapper);
        }catch (NullPointerException e) {return null;}

    }

    @Override
    public List<Site> findAll() {
        return jdbcTemplate.query("select id, name from SITE", this::siteMapper);
    }

    @Override
    public void update(Site element) {
        jdbcTemplate.update("update SITE set name = :name where id = :id" , new MapSqlParameterSource()
                .addValue("id" , element.getId()).addValue("name" , element.getName()));
    }

    @Override
    public void deleteById(String s) {
        jdbcTemplate.update("delete from SITE where id = :id" ,
                new MapSqlParameterSource("id" , s)) ;
    }

    private Site siteMapper(ResultSet rs, int rowNum) throws SQLException {
        Site site = new Site(rs.getString("name"));
        site.setId(rs.getString("id"));
        return site;
    }
}
