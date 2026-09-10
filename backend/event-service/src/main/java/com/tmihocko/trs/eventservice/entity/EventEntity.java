package com.tmihocko.trs.eventservice.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import com.tmihocko.trs.eventservice.dto.CreateEventRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class EventEntity {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;

	@Column(columnDefinition = "TEXT")
	private String name;

	private OffsetDateTime date;

	private Integer capacity;
	
	private Integer ticketsLeft;

	@ManyToOne
	@JoinColumn(name="venueId")
	private VenueEntity venue;

	public Long getEventId() {
		return eventId;
	}
	
	public EventEntity(CreateEventRequest body, VenueEntity venue) {
		if (body.capacity() == null || body.capacity() < 1) {
			throw new IllegalArgumentException("Capacity must be greater than zero");
		}
		this.name = body.name();
		this.date = body.date();
		this.venue = venue;
		this.capacity = body.capacity();
		this.ticketsLeft = body.capacity();
	} 
	// required by JPA
	protected EventEntity() {}
}