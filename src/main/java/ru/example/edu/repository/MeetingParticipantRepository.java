package ru.example.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.example.edu.entity.MeetingParticipant;
import ru.example.edu.entity.Status;

import java.util.Optional;

@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {

    Optional<MeetingParticipant> findByMeetingIdAndPersonId(Long meetingId, Long personId);

    @Modifying
    @Transactional
    @Query("UPDATE MeetingParticipant mp SET mp.status = :status " +
            "WHERE mp.meeting.id = :meetingId AND mp.person.id = :personId")
    void replaceStatus(@Param("status") Status status, @Param("meetingId") Long meetingId,
                       @Param("personId") Long personId);

    @Transactional
    void deleteByMeetingIdAndPersonId(Long meetingId, Long personId);
}
