package ru.example.edu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="start_time")
    private long startTime;

    @Column(name="end_time")
    private long endTime;

    @Column(name="date")
    private long date;

    @Column(name="description")
    private String description;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private Person owner;

    @ToString.Exclude
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MeetingParticipant> meetingParticipants;

}
