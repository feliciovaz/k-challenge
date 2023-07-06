package pt.fvaz.koerber.challenge.controller;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pt.fvaz.koerber.challenge.entity.Trip;
import pt.fvaz.koerber.challenge.entity.Zone;
import pt.fvaz.koerber.challenge.repository.TripRepository;
import pt.fvaz.koerber.challenge.repository.ZoneRepository;

@RestController
public class UploadDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDataController.class);
    private final TripRepository tripRepository;
    private final ZoneRepository zoneRepository;
    private static final DateTimeFormatter formatter = ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public UploadDataController(TripRepository tripRepository, ZoneRepository zoneRepository) {
        this.tripRepository = tripRepository;
        this.zoneRepository = zoneRepository;
    }

    @GetMapping("trips")
    public Collection<Trip> showTrips() {
        return tripRepository.findAll();
    }

    @PostMapping("load-trips")
    public ResponseEntity<?> loadTrips(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines();
        boolean isYellow = isYellowCab(file.getOriginalFilename());
        Collection<Trip> trips = lines.flatMap(line -> toTrip(line, isYellow)).collect(Collectors.toList());
        tripRepository.saveAllAndFlush(trips);

        return ResponseEntity.ok().build();
    }

    @PostMapping("load-zones")
    public ResponseEntity<?> loadZones(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines();
        Collection<Zone> zones = lines.flatMap(UploadDataController::toZone).collect(Collectors.toList());
        zoneRepository.saveAllAndFlush(zones);

        return ResponseEntity.ok().build();
    }

    private static boolean isYellowCab(String file) {
        return file.startsWith("yellow");
    }

    private static int getLocationId(String locId) {
        return Integer.parseInt(locId);
    }

    private static Stream<Trip> toTrip(String input, boolean isYellow) {
        String[] fields = input.split(",");
        if (fields.length < 7) {
            LOGGER.error("invalid number of fields in line: {}", input);
            return Stream.empty();
        }
        try {
            int puZoneId = getLocationId(fields[5]);
            int doZoneId = getLocationId(fields[6]);
            LocalDateTime pickUpTime = LocalDateTime.parse(fields[1], formatter);
            LocalDateTime dropOffTime = LocalDateTime.parse(fields[2], formatter);

            return Stream.of(new Trip(null, puZoneId, doZoneId, pickUpTime, dropOffTime, isYellow));
        } catch (Exception ex) {
            LOGGER.error("cannot process line: {}", input, ex);
            return Stream.empty();
        }
    }

    private static boolean isHeader(String input) {
        return input.startsWith("\"LocationID\"");
    }

    private static Stream<Zone> toZone(String inputLine) {
        String[] fields = inputLine.split(",");
        if (fields.length < 3) {
            LOGGER.error("invalid number of fields in line: {}", inputLine);
            return Stream.empty();
        }
        if (isHeader(inputLine)) {
            return Stream.empty();
        }

        String name = fields[2].replaceAll("^\"|\"$", "");
        if (name.isBlank()) {
            LOGGER.error("discarding line with blank zone name: {}", inputLine);
        }

        try {
            int locationId = getLocationId(fields[0]);
            return Stream.of(new Zone(locationId, name));
        } catch (IllegalArgumentException ex) {
            LOGGER.error("cannot process line: {}", inputLine, ex);
            return Stream.empty();
        }
    }
}
