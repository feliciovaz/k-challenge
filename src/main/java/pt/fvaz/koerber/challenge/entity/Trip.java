package pt.fvaz.koerber.challenge.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_generator")
    @SequenceGenerator(name="trip_generator", sequenceName = "trip_seq", allocationSize = 20)
    @Column(name="id", updatable = false, nullable = false)
    private Long id;

    @Column(name="pu_zid")
    private Integer pickUpZoneId;

    @Column(name="do_zid")
    private Integer dropOffZoneId;

    @Column(name="pickup")
    private LocalDateTime pickUpTime;

    @Column(name="dropoff")
    private LocalDateTime dropOffTime;

    @Column(name="yellow")
    private boolean isYellowCab;

    public Trip() {
    }

    public Trip(Long id, Integer pickUpZoneId, Integer dropOffZoneId, LocalDateTime pickUpTime, LocalDateTime dropOffTime, boolean isYellowCab) {
        this.id = id;
        this.pickUpZoneId = pickUpZoneId;
        this.dropOffZoneId = dropOffZoneId;
        this.pickUpTime = pickUpTime;
        this.dropOffTime = dropOffTime;
        this.isYellowCab = isYellowCab;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPickUpZoneId() {
        return pickUpZoneId;
    }

    public void setPickUpZoneId(Integer pickUpZoneId) {
        this.pickUpZoneId = pickUpZoneId;
    }

    public Integer getDropOffZoneId() {
        return dropOffZoneId;
    }

    public void setDropOffZoneId(Integer dropOffZoneId) {
        this.dropOffZoneId = dropOffZoneId;
    }

    public LocalDateTime getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(LocalDateTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public LocalDateTime getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(LocalDateTime dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public boolean isYellowCab() {
        return isYellowCab;
    }

    public void setYellowCab(boolean yellowCab) {
        isYellowCab = yellowCab;
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id + ", pickUpZoneId=" + pickUpZoneId + ", dropOffZoneId=" + dropOffZoneId + ", pickUpTime=" + pickUpTime + ", dropOffTime="
                + dropOffTime + ", isYellowCab=" + isYellowCab + '}';
    }
}
