package com.bodden.VeterinaryAPI.Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "appointmentHistory")
public class AppointmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    private Appointment appointment;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private LogType log;

    @Column(nullable = false)
    private LocalDateTime date;

    public enum LogType{
        CREATED(),UPDATED(),DELETED();
    }

}
