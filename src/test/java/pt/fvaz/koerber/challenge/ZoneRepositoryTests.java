package pt.fvaz.koerber.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import pt.fvaz.koerber.challenge.entity.Zone;
import pt.fvaz.koerber.challenge.repository.ZoneRepository;

@DataJpaTest
public class ZoneRepositoryTests {

    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    void shouldListZones() {
        List<Zone> zones = zoneRepository.findAll();

        zones.stream().forEach(System.out::println);

        assertNotNull(zones);
        assertEquals(2, zones.size());
    }

    @Test
    void shouldAddZone() {
        zoneRepository.saveAndFlush(new Zone(42,"Downtown"));

        Optional<Zone> createdZone = zoneRepository.findById(42);

        assertTrue(createdZone.isPresent());
        assertEquals("Downtown", createdZone.get().getName());
    }

    @Test
    void shouldFindByName() {
        Collection<Zone> zonesFound = zoneRepository.findByName("Jamaica Bay");

        assertEquals(1, zonesFound.size());
        assertEquals(23, zonesFound.stream().toList().get(0).getId());
        assertEquals("Jamaica Bay", zonesFound.stream().toList().get(0).getName());
    }
}
