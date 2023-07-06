package pt.fvaz.koerber.challenge.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TopZone(@JsonIgnore Integer zoneId, String zone, @JsonProperty("pu_total") Integer puTotal, @JsonProperty("do_total") Integer doTotal) {
}
