package ru.example.edu.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.example.edu.entity.Meeting;
import ru.example.edu.entity.Status;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("SELECT DISTINCT m FROM Meeting m " + "LEFT JOIN m.meetingParticipants mp " +
            "WHERE m.owner.id = :personId OR mp.person.id = :personId")
    List<Meeting> findByPersonId(@Param("personId") Long personId);

    @Query("SELECT DISTINCT m FROM Meeting m " + "LEFT JOIN m.meetingParticipants mp " +
            "WHERE m.date = :date AND ( m.owner.id = :personId OR (mp.person.id = :personId AND mp.status = :status))")
    @EntityGraph(attributePaths = {"meetingParticipants"})
    List<Meeting> findByPersonIdAndDate(@Param("personId") Long personId, @Param("date") Long date,
                                        @Param("status") Status status);


    @Query("SELECT DISTINCT m FROM Meeting m " + "JOIN m.meetingParticipants mp " +
            "WHERE mp.person.id = :personId AND mp.status = :status")
    List<Meeting> getUserInvitations(@Param("personId") Long id, @Param("status") Status status);
}
