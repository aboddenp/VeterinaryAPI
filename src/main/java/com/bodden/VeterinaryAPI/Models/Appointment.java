package com.bodden.VeterinaryAPI.Models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Service service;

    @Basic
    @Column(nullable = false)
    private LocalDate localDate;

    @Basic
    @Column(nullable = false)
    private LocalTime localTime;

    @OneToOne
    private Pet petClient;

    @OneToOne
    private User userClient;

    public enum Service{
        GROOMING(),MEDICAL()
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, localDate, localTime, petClient);
    }
}
