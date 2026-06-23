package ru.example.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.edu.dto.MeetingDTO;
import ru.example.edu.entity.Status;
import ru.example.edu.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping("/person/{personId}")
    public List<MeetingDTO> getMeetingByPersonId(@PathVariable("personId") Long personId)
    {return meetingService.getMeetingByPersonId(personId);}

    @GetMapping("/person/{personId}/date")
    public List<MeetingDTO> getMeetingByPersonIdAndDate(@PathVariable("personId") Long personId,
                                                               @RequestParam("date") Long date){
        return meetingService.getMeetingByPersonIdAndDate(personId, date);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MeetingDTO> createMeeting(@RequestBody MeetingDTO meetingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(meetingDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @PatchMapping("/{id}/person/{personId}/status")
    public ResponseEntity<Void> replaceStatus(@PathVariable("id") Long id, @PathVariable("personId") Long personId,
                                              @RequestParam("status")Status status) {
        meetingService.replaceStatus(id, personId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/person/{personId}/invitations")
    public List<MeetingDTO> getInvitations(@PathVariable("personId") Long id){
        return meetingService.getInvitations(id);
    }
}
