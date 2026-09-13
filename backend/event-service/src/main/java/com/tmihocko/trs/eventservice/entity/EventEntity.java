package com.tmihocko.trs.eventservice.entity;

import java.time.OffsetDateTime;

import com.tmihocko.trs.eventservice.dto.PostEvent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "events")
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

	public EventEntity(PostEvent body, VenueEntity venue) {
		if (body.capacity() == null || body.capacity() < 1) {
			throw new IllegalArgumentException("Capacity must be greater than zero");
		}
		this.name = body.name();
		this.date = body.date();
		this.venue = venue;
		this.capacity = body.capacity();
		this.ticketsLeft = body.capacity();
	} 

	// Addition, not assignment
	public void changeTicketsLeft(Integer amount) {
		int updatedTicketsLeft = ticketsLeft + amount;

		if (updatedTicketsLeft < 0 || updatedTicketsLeft > capacity) {
			throw new IllegalStateException(
				"Invalid tickets left value: " + updatedTicketsLeft
			);
		}

		ticketsLeft = updatedTicketsLeft;
	}	

	public Long getEventId() {
		return eventId;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public OffsetDateTime getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public Integer getTicketsLeft() {
		return ticketsLeft;
	}

	public VenueEntity getVenue() {
		return venue;
	}

	// required by JPA
	protected EventEntity() {}
}