package ru.example.edu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "meeting_participants")
public class MeetingParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
}
