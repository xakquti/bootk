package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.MeetingParticipantDTO;
import ru.example.edu.entity.MeetingParticipant;

@UtilityClass
public class MeetingParticipantMapper {
    public MeetingParticipantDTO convertToDto(MeetingParticipant meetingParticipant) {
        MeetingParticipantDTO meetingParticipantDTO = new MeetingParticipantDTO();
        meetingParticipantDTO.setUserId(meetingParticipant.getPerson().getId());
        meetingParticipantDTO.setUserName(meetingParticipant.getPerson().getUsername());
        meetingParticipantDTO.setStatus(meetingParticipant.getStatus());
        return meetingParticipantDTO;
    }
}
