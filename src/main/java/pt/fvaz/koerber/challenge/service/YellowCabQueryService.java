package pt.fvaz.koerber.challenge.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pt.fvaz.koerber.challenge.entity.Trip;
import pt.fvaz.koerber.challenge.repository.TripRepository;

@Service
public class YellowCabQueryService {

    private final TripRepository tripRepository;

    YellowCabQueryService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    Collection<Trip> getYellowCabTrips() {
        return tripRepository.findByIsYellowCab(true);
    }

    Page<Trip> getPagedYellowCabTrips(Pageable pageable) {
        return tripRepository.findByIsYellowCab(true, pageable);
    }
}
