package pt.fvaz.koerber.challenge.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.fvaz.koerber.challenge.entity.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
