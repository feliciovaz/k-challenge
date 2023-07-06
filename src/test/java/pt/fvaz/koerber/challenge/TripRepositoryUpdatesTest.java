package pt.fvaz.koerber.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import pt.fvaz.koerber.challenge.entity.Trip;
import pt.fvaz.koerber.challenge.repository.TripRepository;

@DataJpaTest
public class TripRepositoryUpdatesTest {

    @Autowired
    private TripRepository tripRepository;

    @Test
    void shouldAddTrip() {
        tripRepository.saveAndFlush(
                new Trip(null, 124, 146,
                        LocalDateTime.of(2018,1,2,6,18,50),
                        LocalDateTime.of(2018,1,2,7,0,59),
                        false));

        Optional<Trip> createdTrip = tripRepository.findByPickUpZoneId(124L);

        assertTrue(createdTrip.isPresent());
        assertTrue(createdTrip.get().getId() > 0);
        assertEquals(124, createdTrip.get().getPickUpZoneId());
        assertEquals(146, createdTrip.get().getDropOffZoneId());
    }


}
