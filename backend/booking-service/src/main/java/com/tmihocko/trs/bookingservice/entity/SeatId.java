package com.tmihocko.trs.bookingservice.entity;

import java.io.Serializable;
import java.util.Objects;


public class SeatId implements Serializable {

    Long eventId;
    String seatName;

    public SeatId() {}

    public SeatId(Long eventId, String seatName) {
        this.eventId = eventId;
        this.seatName = seatName;
    }

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;

		if (!(object instanceof SeatId other)) return false;

		return Objects.equals(eventId, other.eventId)
			&& Objects.equals(seatName, other.seatName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventId, seatName);
	}
}