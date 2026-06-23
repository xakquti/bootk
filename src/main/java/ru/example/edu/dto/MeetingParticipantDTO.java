package ru.example.edu.dto;

import lombok.Data;
import ru.example.edu.entity.Status;

@Data
public class MeetingParticipantDTO {
    private long userId;
    private String userName;
    private Status status;
}
