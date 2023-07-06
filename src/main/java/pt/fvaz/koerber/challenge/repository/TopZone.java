package pt.fvaz.koerber.challenge.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TopZone(@JsonIgnore Long zoneId, String zone, @JsonProperty("pu_total") Long puTotal, @JsonProperty("do_total") Long doTotal) {
}
