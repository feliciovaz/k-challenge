package pt.fvaz.koerber.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.fvaz.koerber.challenge.repository.ZoneTrip;
import pt.fvaz.koerber.challenge.service.ReportService;


@RestController
public class ReportController {

    private final ReportService reportService;

    ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("top-zones")
    ResponseEntity<TopZoneResult> getTopZones(@RequestParam("order") String order) {
        TopZoneResult result = reportService.getTopZones(order);

        if (result != null) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("zone-trips")
    public ResponseEntity<ZoneTrip> getZoneTrips(@RequestParam("date") String date, @RequestParam("zone") String zoneName) {
        ZoneTrip zoneTrip = reportService.getZoneTrip(date, zoneName);
        if (zoneTrip == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(zoneTrip);
    }
}
