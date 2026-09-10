package com.tmihocko.trs.eventservice;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tmihocko.trs.eventservice.dto.CreateEventRequest;
import com.tmihocko.trs.eventservice.entity.EventEntity;
import com.tmihocko.trs.eventservice.entity.VenueEntity;
import com.tmihocko.trs.eventservice.repository.EventRepository;
import com.tmihocko.trs.eventservice.repository.VenueRepository;

import jakarta.transaction.Transactional;

@Service 
public class EventService {
	private final EventRepository eventRepository;
	private final VenueRepository venueRepository;

	public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
		this.eventRepository = eventRepository;
		this.venueRepository = venueRepository;

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
	public EventEntity createEventAndPublish(CreateEventRequest body) {
		String venueName = body.venueName().trim();

		VenueEntity venue = venueRepository
			.findByNameIgnoreCase(venueName)
			.orElseGet(() -> venueRepository.save(new VenueEntity(venueName)));

		EventEntity entity = new EventEntity(body, venue);

		saveEvent(entity);

		List<String> seatNames = (body.seatNames() == null || body.seatNames().isEmpty())
					? IntStream
						.rangeClosed(1, body.capacity())
						.mapToObj(String::valueOf)
						.toList()
					: List.copyOf(body.seatNames());

		// post("api/booking", body);
		// wait for this to finish

		return entity;
	}
	
	@Transactional 
	private void saveEvent(EventEntity entity) {
		
		eventRepository.save(entity);
	}
}
