package com.tmihocko.trs.bookingservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmihocko.trs.bookingservice.BookingService;
import com.tmihocko.trs.bookingservice.dto.InitializeEventRequest;

import jakarta.validation.Valid;


/**
 * Weirdly named and endpointed,
 * 
 * Used ONLY by event service to notify booking service that an event has been created/destroyed
 */

@RestController 
@RequestMapping ("/internal/events")
public class InternalEventController {

    private final BookingService bookingService;

    public InternalEventController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping 
    public ResponseEntity<Void> initializeEvent(
		@Valid @RequestBody InitializeEventRequest request
	) {
		bookingService.initializeEvent(
				request.eventId(),
				request.seatNames()
		);

		return ResponseEntity.noContent().build();
    }
}