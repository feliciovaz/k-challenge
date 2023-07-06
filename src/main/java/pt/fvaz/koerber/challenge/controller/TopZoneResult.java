package pt.fvaz.koerber.challenge.controller;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import pt.fvaz.koerber.challenge.repository.TopZone;

public record TopZoneResult(@JsonProperty("top_zones") Collection<TopZone> topZones) {}
