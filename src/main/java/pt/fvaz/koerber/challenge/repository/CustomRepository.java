package pt.fvaz.koerber.challenge.repository;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepository {

    private final JdbcTemplate jdbcTemplate;

    CustomRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<TopZone> getTopZonesByPickUps() {
        String query = "select z.id as zoneId, z.name as zoneName, count(1) as cnt from trip as t JOIN zone as z on t.pu_zid = z.id group by pu_zid order by cnt desc limit 5";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            return new TopZone(rs.getInt("zoneId"), rs.getString("zoneName"), rs.getInt("cnt"), 0);
        });
    }

    public Integer getDropOffsForZoneId(int zoneId) {
        String query = "select count(1) as cnt from trip where do_zid=?";
        return jdbcTemplate.queryForObject(query, Integer.class, zoneId);
    }

    public Collection<TopZone> getTopZonesByDropOffs() {
        String query = "select z.id as zoneId, z.name as zoneName, count(1) as cnt from trip as t JOIN zone as z on t.do_zid = z.id group by do_zid order by cnt desc limit 5";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            return new TopZone(rs.getInt("zoneId"), rs.getString("zoneName"), 0, rs.getInt("cnt"));
        });
    }

    public Integer getPickUpsForZoneId(int zoneId) {
        String query = "select count(1) as cnt from trip where pu_zid=?";
        return jdbcTemplate.queryForObject(query, Integer.class, zoneId);
    }

}
