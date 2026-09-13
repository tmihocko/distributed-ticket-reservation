package com.tmihocko.trs.contracts;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public record BookingEvent(
	UUID messageId,
	Long eventId,
	BookingType type,
	List<Booking> bookings,
	Instant occurredAt
) {
	public BookingEvent {
		Objects.requireNonNull(messageId);
		Objects.requireNonNull(eventId);
		Objects.requireNonNull(type);
		Objects.requireNonNull(bookings);
		Objects.requireNonNull(occurredAt);

		if (bookings.isEmpty()) {
			throw new IllegalArgumentException("Booking event must contain at least one booking");
		}

		bookings = List.copyOf(bookings);
	}
	
	public static BookingEvent batch(
		Long eventId,
		BookingType type,
		List<Booking> bookings
	) {
		return new BookingEvent(UUID.randomUUID(), eventId, type, bookings, Instant.now());
	}

	public enum BookingType {
		CREATED,
		DELETED,
	}

	public record Booking(
		Long bookingId,
		String seatName
	) {
		public Booking {
			Objects.requireNonNull(bookingId);
			Objects.requireNonNull(seatName);

			if (seatName.isBlank()) {
				throw new IllegalArgumentException( "Seat name cannot be blank");
			}
		}
	}
}