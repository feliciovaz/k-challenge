package pt.fvaz.koerber.challenge.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import pt.fvaz.koerber.challenge.entity.Trip;
import pt.fvaz.koerber.challenge.repository.TripRepository;

@DataJpaTest
class YellowCabQueryServiceTest {

    @Autowired
    private TripRepository tripRepository;

    @Test
    void getYellowCabTrips() {
        YellowCabQueryService service = new YellowCabQueryService(tripRepository);
        Collection<Trip> trips = service.getYellowCabTrips();

        trips.stream().forEach(System.out::println);

        assertEquals(3, trips.size());
        assertEquals(3, trips.stream().filter(Trip::isYellowCab).count());
    }

    @Test
    void getYellowCabTripsPageable() {
        YellowCabQueryService service = new YellowCabQueryService(tripRepository);
        Pageable pageable = PageRequest.of(0, 2, Sort.by("PickUpTime"));
        Page<Trip> pagedTrips = service.getPagedYellowCabTrips(pageable);

        pagedTrips.stream().forEach(System.out::println);

        List<Trip> tripsInPage = pagedTrips.stream().collect(Collectors.toList());
        assertEquals(2, tripsInPage.size());
        assertEquals(23, tripsInPage.get(0).getPickUpZoneId());
        assertEquals(45, tripsInPage.get(0).getDropOffZoneId());
        assertEquals(22, tripsInPage.get(1).getPickUpZoneId());
        assertEquals(23, tripsInPage.get(1).getDropOffZoneId());
        assertEquals(3, pagedTrips.getTotalElements());
        assertEquals(2, pagedTrips.getTotalPages());
    }

}