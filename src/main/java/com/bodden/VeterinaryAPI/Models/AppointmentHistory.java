package com.bodden.VeterinaryAPI.Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Appointment appointment;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private LogType log;

    @Column(nullable = false)
    private LocalDateTime date;

    public enum LogType{
        CREATED(),UPDATED()
    }

}
