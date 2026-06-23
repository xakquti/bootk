package ru.example.edu.service;

import ru.example.edu.dto.MeetingDTO;
import ru.example.edu.entity.Status;

import java.util.List;

public interface MeetingService {

    List<MeetingDTO> getMeetingByPersonId(Long personId);

    List<MeetingDTO> getMeetingByPersonIdAndDate(Long personId, Long date);

    void deleteMeeting(Long id);

    MeetingDTO createMeeting(MeetingDTO dto);

    MeetingDTO getMeetingById(Long id);

    List<MeetingDTO> getInvitations(Long personId);

    void replaceStatus(Long id, Long personId, Status status);

}
