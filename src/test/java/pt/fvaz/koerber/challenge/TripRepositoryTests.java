package pt.fvaz.koerber.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import pt.fvaz.koerber.challenge.entity.Trip;
import pt.fvaz.koerber.challenge.repository.TopZoneAux;
import pt.fvaz.koerber.challenge.repository.TripRepository;

@DataJpaTest
public class TripRepositoryTests {

    @Autowired
    private TripRepository tripRepository;

    @Test
    void shouldListTrips() {
        List<Trip> trips = tripRepository.findAll();

        trips.stream().forEach(System.out::println);

        assertNotNull(trips);
        assertEquals(1, trips.size());

        Trip trip = trips.get(0);
        assertEquals(1, trip.getId());
        assertEquals(23, trip.getPickUpZoneId());
        assertEquals(45, trip.getDropOffZoneId());
        assertTrue(trip.isYellowCab());
        assertEquals(LocalDateTime.of(2018,1,1,0,18,50), trip.getPickUpTime());
        assertEquals(LocalDateTime.of(2018,1,1,0,24,39), trip.getDropOffTime());

    }

    @Test
    void shouldAddTrip() {
        tripRepository.saveAndFlush(
                new Trip(null, 24, 46,
                        LocalDateTime.of(2018,1,2,6,18,50),
                        LocalDateTime.of(2018,1,2,7,0,59),
                        false));

        Optional<Trip> createdTrip = tripRepository.findById(2L);

        assertTrue(createdTrip.isPresent());
        assertEquals(2, createdTrip.get().getId());
        assertEquals(24, createdTrip.get().getPickUpZoneId());
        assertEquals(46, createdTrip.get().getDropOffZoneId());
    }

    @Test
    void shouldCountPickusForZoneAndDay() {
        int pus = tripRepository.countPickUpsForZoneAndDay(
                LocalDateTime.of(2018,1,1,0,0,0),
                LocalDateTime.of(2018,1,2,0,0,0),
                23);
        assertEquals(1, pus);
    }

    @Test
    void shouldCountDropOffsForZoneAndDay() {
        int dropOffs = tripRepository.countDropOffsForZoneAndDay(
                LocalDateTime.of(2018,1,1,0,0,0),
                LocalDateTime.of(2018,1,2,0,0,0),
                45);
        assertEquals(1, dropOffs);
    }

    @Test
    void shoudGetTopZonesByPickUpZone() {
        Collection<TopZoneAux> topInfo = tripRepository.topPickUpsByZoneId();

        topInfo.stream().forEach(System.out::println);
    }
}
