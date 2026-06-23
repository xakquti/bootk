package ru.example.edu.exception;

public class MeetingParticipantNotFoundException extends RuntimeException {
    public MeetingParticipantNotFoundException(String message) {
        super(message);
    }
}
