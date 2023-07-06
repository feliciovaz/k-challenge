package pt.fvaz.koerber.challenge.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.fvaz.koerber.challenge.entity.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {
    Collection<Zone> findByName(String name);
}
