package pt.fvaz.koerber.challenge.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ZoneTrip(String zone, String date, @JsonProperty("pu") Integer pickUps, @JsonProperty("do") Integer dropOffs) {}
