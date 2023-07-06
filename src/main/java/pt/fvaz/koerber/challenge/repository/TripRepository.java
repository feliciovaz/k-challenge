package pt.fvaz.koerber.challenge.repository;


import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.fvaz.koerber.challenge.entity.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query( value = "select count(t) from Trip t where t.pickUpTime >= :begin and t.pickUpTime < :end and pickUpZoneId = :zoneId")
    int countPickUpsForZoneAndDay(@Param("begin")
    LocalDateTime begin, @Param("end") LocalDateTime end, @Param("zoneId") int zoneId);

    @Query( value = "select count(t) from Trip t where t.dropOffTime >= :begin and t.dropOffTime < :end and dropOffZoneId = :zoneId")
    int countDropOffsForZoneAndDay(@Param("begin")LocalDateTime begin, @Param("end") LocalDateTime end, @Param("zoneId") int zoneId);

}
