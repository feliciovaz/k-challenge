package pt.fvaz.koerber.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import pt.fvaz.koerber.challenge.entity.Trip;
import pt.fvaz.koerber.challenge.repository.TopZoneAux;
import pt.fvaz.koerber.challenge.repository.TripRepository;

@DataJpaTest
public class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Test
    void shouldListTrips() {
        List<Trip> trips = tripRepository.findAll();

        //trips.stream().forEach(System.out::println);

        assertNotNull(trips);
        assertEquals(4, trips.size());

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

        //topInfo.stream().forEach(System.out::println);

        assertEquals(3, topInfo.size());
        assertEquals(23, topInfo.stream().toList().get(0).id());
        assertEquals(2, topInfo.stream().toList().get(0).count());
    }

    @Test
    void shoudGetTopZonesByDropOffZone() {
        Collection<TopZoneAux> topInfo = tripRepository.topDropOffsByZoneId();

        //topInfo.stream().forEach(System.out::println);

        assertEquals(3, topInfo.size());
        assertEquals(46, topInfo.stream().toList().get(0).id());
        assertEquals(2, topInfo.stream().toList().get(0).count());
    }

}
