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
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "owner_animal",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    @ToString.Exclude
    private Set<Pet> petsOwned;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName);
    }
}
