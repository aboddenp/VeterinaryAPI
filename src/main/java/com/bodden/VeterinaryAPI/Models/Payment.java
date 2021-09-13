package com.bodden.VeterinaryAPI.Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Payments")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Appointment appointment;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean payed;
}
