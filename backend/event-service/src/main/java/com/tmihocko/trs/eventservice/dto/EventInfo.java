package com.tmihocko.trs.eventservice.dto;

import java.time.OffsetDateTime;

import com.tmihocko.trs.eventservice.entity.EventEntity;
import com.tmihocko.trs.eventservice.entity.VenueEntity;

public record EventInfo( 
	Long eventId,
	String name,
	OffsetDateTime date,
	Integer capacity,
	Integer ticketsLeft,
	VenueInfo venue) {


	public static EventInfo from(EventEntity event) {
		VenueEntity venue = event.getVenue();

		return new EventInfo(
			event.getEventId(),
			event.getName(),
			event.getDate(),
			event.getCapacity(),
			event.getTicketsLeft(),
			new VenueInfo(
					venue.getId(),
					venue.getName()
			)
		);
	}

	public record VenueInfo(
            Long venueId,
            String name
    ) {}
}
