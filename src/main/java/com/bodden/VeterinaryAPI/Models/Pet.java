package com.bodden.VeterinaryAPI.Models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Pet")
public class Pet {
    // Fields
    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specie;

    private String Breed;

    @OneToMany
    @ToString.Exclude
    private Set<Appointment> appointmentsHistory;

    @ManyToMany(mappedBy = "petsOwned")
    @ToString.Exclude
    private Set<User> users;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, specie);
    }
}
