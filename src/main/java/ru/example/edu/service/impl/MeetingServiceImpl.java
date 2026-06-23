package ru.example.edu.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.MeetingDTO;
import ru.example.edu.entity.Meeting;
import ru.example.edu.entity.MeetingParticipant;
import ru.example.edu.entity.Person;
import ru.example.edu.entity.Status;
import ru.example.edu.exception.MeetingNotFoundException;
import ru.example.edu.exception.MeetingParticipantNotFoundException;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.MeetingParticipantRepository;
import ru.example.edu.repository.MeetingRepository;
import ru.example.edu.repository.PersonRepository;
import ru.example.edu.service.MeetingService;
import ru.example.edu.util.MeetingMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;



    @Override
    public List<MeetingDTO> getMeetingByPersonId(Long personId) {
        if (personRepository.findById(personId).isEmpty()) {
            throw new PersonNotFoundException("Пользователь не найдён!!!");
        }
        return meetingRepository.findByPersonId(personId).stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getMeetingByPersonIdAndDate(Long personId, Long date) {
        if (personRepository.findById(personId).isEmpty()) {
            throw new PersonNotFoundException("Пользователь не найдён!!!");
        }

        return meetingRepository.findByPersonIdAndDate(personId, date, Status.ACCEPTED).stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MeetingDTO createMeeting(MeetingDTO dto) {

        Optional<Person> person = personRepository.findByUsername(dto.getOwnerName());
        if (person.isEmpty()) {
            throw new PersonNotFoundException("Пользователь не найдён!!!");
        }

        Meeting meeting = new Meeting();
        meeting.setName(dto.getName());
        meeting.setOwner(person.get());
        meeting.setDescription(dto.getDescription());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());
        meeting.setDate(dto.getDate());

        Meeting savedMeeting = meetingRepository.saveAndFlush(meeting);

        if (dto.getParticipants() != null) {
            Set<MeetingParticipant> participants = dto.getParticipants().stream().map(p ->
            {
                Person invPerson = personRepository.findById(p.getUserId()).orElseThrow(() ->
                        new PersonNotFoundException("Пользователь не найдён!!!"));

                MeetingParticipant participant = new MeetingParticipant();
                participant.setPerson(invPerson);
                participant.setMeeting(savedMeeting);
                participant.setStatus(p.getStatus());
                meetingParticipantRepository.saveAndFlush(participant);
                return participant;
            }).collect(Collectors.toSet());
            savedMeeting.setMeetingParticipants(participants);
        }

        return MeetingMapper.convertToDto(savedMeeting);
    }

    @Override
    public MeetingDTO getMeetingById(Long id) {
        return meetingRepository.findById(id).map(MeetingMapper::convertToDto).orElseThrow(() ->
                new MeetingNotFoundException("Встреча не найдена!!!"));
    }

    @Override
    public List<MeetingDTO> getInvitations(Long personId) {
        return meetingRepository.getUserInvitations(personId, Status.PENDING).stream()
                .map(MeetingMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void replaceStatus(Long id, Long personId, Status status) {
        meetingParticipantRepository.replaceStatus(status, id, personId);
    }
}
