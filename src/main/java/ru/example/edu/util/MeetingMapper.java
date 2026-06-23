package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.MeetingDTO;
import ru.example.edu.entity.Meeting;
import ru.example.edu.entity.Person;

import java.util.stream.Collectors;

@UtilityClass
public class MeetingMapper {
    public MeetingDTO convertToDto(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setId(meeting.getId());
        meetingDTO.setName(meeting.getName());
        meetingDTO.setDescription(meeting.getDescription());
        meetingDTO.setStartTime(meeting.getStartTime());
        meetingDTO.setEndTime(meeting.getEndTime());
        meetingDTO.setDate(meeting.getDate());
        meetingDTO.setOwnerName(meeting.getOwner().getUsername());
        meetingDTO.setParticipants(meeting.getMeetingParticipants()
                .stream().map(MeetingParticipantMapper::convertToDto).collect(Collectors.toSet()));
        return meetingDTO;
    }
}
