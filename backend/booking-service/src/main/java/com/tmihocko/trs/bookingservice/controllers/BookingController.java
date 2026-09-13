package com.tmihocko.trs.bookingservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmihocko.trs.bookingservice.BookingService;
import com.tmihocko.trs.bookingservice.dto.PostBooking;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	BookingService bookingService;

	BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	public ResponseEntity<Long> bookSeat(
		@Valid @RequestBody PostBooking body
	) {
		
		Long bookingId = bookingService.bookSeat(body);
		// tell kafka that a seat was booked for event id

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(bookingId);
	}

	@DeleteMapping("/{bookingId}")
	public ResponseEntity<Void> unbookSeat(
		@PathVariable Long bookingId
	) {
		bookingService.unbookSeat(bookingId);

    	return ResponseEntity.noContent().build();
	}

	@GetMapping("/events/{eventId}/seats/{seatName}")
	public ResponseEntity<Boolean> seatIsBooked(
		@PathVariable Long eventId,
		@PathVariable String seatName
	) {
		return ResponseEntity.ok(
			bookingService.seatIsBooked(eventId, seatName)
		);
	}

}
