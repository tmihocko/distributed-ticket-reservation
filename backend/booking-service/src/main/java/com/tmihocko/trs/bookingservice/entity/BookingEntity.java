package com.tmihocko.trs.bookingservice.entity;

import java.time.LocalDateTime;

import com.tmihocko.trs.bookingservice.dto.PostBooking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "bookings")
public class BookingEntity {

	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long bookingId;

	Long eventId;

	String userId;

	String seatName;

	LocalDateTime bookedAt;

	public BookingEntity(PostBooking body) {
		this.seatName = body.seatName();
		this.userId = body.userId();
		this.eventId = body.eventId();
		this.bookedAt = LocalDateTime.now();
	}

	public Long getEventId() {
		return eventId;
	}

	public String getSeatName() {
		return seatName;
	}

	public Long getBookingId() {
		return bookingId;
	}
	
	protected BookingEntity() {}
}
