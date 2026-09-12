package com.tmihocko.trs.bookingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;


@Entity 
@IdClass(SeatId.class)
public class SeatEntity {

	@Id 
	Long eventId;

	@Id
	String seatName;

	boolean booked;

	Long bookingId;

	public SeatEntity(Long eventId, String seatName) {
		this.eventId = eventId;
		this.seatName = seatName;
		this.booked = false;
		this.bookingId = null;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public boolean getBooked() {
		return booked;
	}

	protected SeatEntity() {}
}
