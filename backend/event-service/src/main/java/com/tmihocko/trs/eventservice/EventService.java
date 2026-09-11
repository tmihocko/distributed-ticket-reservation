package com.tmihocko.trs.eventservice;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import com.tmihocko.trs.eventservice.dto.CreateEventRequest;
import com.tmihocko.trs.eventservice.entity.EventEntity;
import com.tmihocko.trs.eventservice.entity.VenueEntity;
import com.tmihocko.trs.eventservice.repository.EventRepository;
import com.tmihocko.trs.eventservice.repository.VenueRepository;

import jakarta.transaction.Transactional;

@Service 
public class EventService {
	private final EventClient eventClient;
	private final EventRepository eventRepository;
	private final VenueRepository venueRepository;

	public EventService(EventRepository eventRepository, VenueRepository venueRepository, EventClient eventClient) {
		this.eventRepository = eventRepository;
		this.venueRepository = venueRepository;
		this.eventClient = eventClient;

	}	
	public EventEntity getEvent(Long id) {
		return eventRepository
			.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
	}

	public List<EventEntity> getEvents(String name, OffsetDateTime date, Long venueId) {
		return eventRepository.search(name, date, venueId);
	}

	/**
	 * Synchronous, waits for postservice to save
	 * 
	 * Users are likely to add bookings immediately after event creation, 
	 * and would prefer to see loading for ~3 seconds than have error when booking immediately
	 * 
	 */
	@Transactional 
	public EventEntity createEventAndPublish(CreateEventRequest body) {
		String venueName = body.venueName().trim();

		VenueEntity venue = venueRepository
			.findByNameIgnoreCase(venueName)
			.orElseGet(() -> venueRepository.save(new VenueEntity(venueName)));

		EventEntity eventEntity = new EventEntity(body, venue);

		eventRepository.save(eventEntity);

		List<String> seatNames = (body.seatNames() == null || body.seatNames().isEmpty())
					? IntStream
						.rangeClosed(1, body.capacity())
						.mapToObj(String::valueOf)
						.toList()
					: List.copyOf(body.seatNames());

		// Make retry later
		HttpStatusCode statusCode;

		try {
			statusCode = eventClient.postEventToBooking(
				eventEntity,
				seatNames
			);
		} catch (ResourceAccessException exception) {
			throw new ResponseStatusException(
				HttpStatus.SERVICE_UNAVAILABLE,
				"Could not connect to Booking Service",
				exception
			);
		}

		if (!statusCode.is2xxSuccessful()) {
			throw new ResponseStatusException(
				HttpStatus.BAD_GATEWAY,
				"Booking Service returned status "+ statusCode.value()
			);
		}

		return eventEntity;
	}
}
