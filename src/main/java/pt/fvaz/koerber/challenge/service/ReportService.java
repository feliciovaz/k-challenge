package pt.fvaz.koerber.challenge.service;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pt.fvaz.koerber.challenge.controller.TopZoneResult;
import pt.fvaz.koerber.challenge.entity.Zone;
import pt.fvaz.koerber.challenge.repository.TopZone;
import pt.fvaz.koerber.challenge.repository.TopZoneAux;
import pt.fvaz.koerber.challenge.repository.TripRepository;
import pt.fvaz.koerber.challenge.repository.ZoneRepository;
import pt.fvaz.koerber.challenge.repository.ZoneTrip;

@Service
public class ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
    private static final DateTimeFormatter dayFormatter = ofPattern("yyyy-MM-dd", Locale.getDefault());

    private final TripRepository tripRepository;
    private final ZoneRepository zoneRepository;

    ReportService(TripRepository tripRepository, ZoneRepository zoneRepository) {
        this.tripRepository = tripRepository;
        this.zoneRepository = zoneRepository;
    }

    public ZoneTrip getZoneTrip(String date, String zoneName) {
        LocalDateTime beginDateTime = LocalDate.parse(date, dayFormatter).atStartOfDay();
        LocalDateTime endDateTime = beginDateTime.plus(1, ChronoUnit.DAYS);

        Zone zone = getZone(zoneName);
        if (zone == null) {
            LOGGER.warn("could not find zone with name: {}", zoneName);
            return null;
        }

        int puCount = tripRepository.countPickUpsForZoneAndDay(beginDateTime, endDateTime, zone.getId());
        int doCount = tripRepository.countDropOffsForZoneAndDay(beginDateTime, endDateTime, zone.getId());

        return new ZoneTrip(zoneName, date, puCount, doCount);
    }

    private Zone getZone(String name) {
        Collection<Zone> zones = zoneRepository.findByName(name);
        if (zones.isEmpty()) {
            return null;
        }
        if (zones.size() > 1) {
            LOGGER.warn("found more than one zone with name: {}", name);
        }
        return zones.stream().toList().get(0);
    }

    private String getZoneName(long id) {
        return zoneRepository.findById(id).map(Zone::getName).orElse("");
    }

    public TopZoneResult getTopZones(String order) {
        if ("pickups".equals(order)) {
            Collection<TopZoneAux> topZonesForPickUps = tripRepository.topPickUpsByZoneId();
            Collection<TopZone> topZones = topZonesForPickUps
                    .stream()
                    .map(tz -> new TopZone(tz.id(), getZoneName(tz.id()), tz.count(), tripRepository.dropOffsByZoneId(tz.id())))
                    .collect(Collectors.toList());
            return new TopZoneResult(topZones);
        } else if ("dropoffs".equals(order)) {
            Collection<TopZoneAux> topZonesForDropOffs = tripRepository.topDropOffsByZoneId();
            Collection<TopZone> topZones = topZonesForDropOffs
                    .stream()
                    .map(tz -> new TopZone(tz.id(), getZoneName(tz.id()), tz.count(), tripRepository.pickUpsByZoneId(tz.id())))
                    .collect(Collectors.toList());
            return new TopZoneResult(topZones);
        }
        LOGGER.error("unsupported order input parameter: {}", order);
        return null;
    }

}
