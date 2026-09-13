package com.tmihocko.trs.contracts;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;


public record BookingEvent(
	UUID messageId,
	Long eventId,
	BookingType type,
	Long bookingId,
	String seatName,
	Instant occurredAt
) {
	public BookingEvent {
		Objects.requireNonNull(messageId);
		Objects.requireNonNull(eventId);
		Objects.requireNonNull(type);
		Objects.requireNonNull(bookingId);
		Objects.requireNonNull(seatName);
		Objects.requireNonNull(occurredAt);

		if (seatName.isBlank()) {
			throw new IllegalArgumentException( "Seat name cannot be blank");
		}
	}
	
	public BookingEvent(Long eventId, BookingType type, Long bookingId, String seatName) {
		this(UUID.randomUUID(), eventId, type, bookingId, seatName, Instant.now());
	}

	public enum BookingType {
		CREATED,
		DELETED,
	}
}