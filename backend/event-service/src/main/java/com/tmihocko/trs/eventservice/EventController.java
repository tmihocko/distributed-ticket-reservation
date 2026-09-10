package com.tmihocko.trs.eventservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tmihocko.trs.eventservice.dto.CreateEventRequest;
import com.tmihocko.trs.eventservice.dto.EventInfo;
import com.tmihocko.trs.eventservice.entity.EventEntity;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/events")
public class EventController {
	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/{id}")
	public EventInfo getEventById(@PathVariable Long id){ 
		return EventInfo.from(eventService.getEvent(id));
	}
	
	
	@GetMapping
	public List<EventInfo> getEvents(
		@RequestParam(required = false) String name, 
		@RequestParam(required = false) OffsetDateTime date,
		@RequestParam(required = false) Long venueId
	) {
		List<EventEntity> events = eventService.getEvents(name, date, venueId);

		List<EventInfo> eventInfos = events
					.stream()
					.map(EventInfo::from)
					.collect(Collectors.toList());

		return eventInfos;
	}

	@PostMapping("")
	public ResponseEntity<Long> createEvent(@RequestBody CreateEventRequest entity) {
		
		EventEntity savedEvent = eventService.createEventAndPublish(entity);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedEvent.getEventId())
			.toUri();

		return ResponseEntity.created(location).body(savedEvent.getEventId());
	}
	


}
