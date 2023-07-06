package pt.fvaz.koerber.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Zone {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    private Integer id;

    @Column(name="name", nullable = false)
    private String name;

    public Zone() {
    }

    public Zone(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Zone{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
