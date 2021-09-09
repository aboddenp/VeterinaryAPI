package com.bodden.VeterinaryAPI.Models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Double amount;

    private String description;

    @OneToOne
    private User client;

    @OneToOne
    private Appointment appointment;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, client, appointment);
    }
}
