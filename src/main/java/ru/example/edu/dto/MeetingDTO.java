package ru.example.edu.dto;

import lombok.Data;
import java.util.Set;

@Data
public class MeetingDTO {
    private long id;
    private String name;
    private long startTime;
    private long endTime;
    private long date;
    private String description;
    private String ownerName;
    private Set<MeetingParticipantDTO> participants;
}
