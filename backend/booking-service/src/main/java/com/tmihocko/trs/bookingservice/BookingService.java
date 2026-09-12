package com.tmihocko.trs.bookingservice;

import com.tmihocko.trs.bookingservice.repository.SeatRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tmihocko.trs.bookingservice.dto.PostBooking;
import com.tmihocko.trs.bookingservice.entity.BookingEntity;
import com.tmihocko.trs.bookingservice.entity.SeatEntity;
import com.tmihocko.trs.bookingservice.entity.SeatId;
import com.tmihocko.trs.bookingservice.repository.BookingRepository;

@Service 
public class BookingService {

	private final SeatRepository seatRepository;
	BookingRepository bookingRepository;

	BookingService(BookingRepository bookingRepository, SeatRepository seatRepository) {
		this.bookingRepository = bookingRepository;
		this.seatRepository = seatRepository;
	}
	
	@Transactional
	public Long bookSeat(PostBooking body) {
		SeatEntity seat = seatRepository
				.findForBooking(body.eventId(), body.seatName())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Seat not found"
				));

		if (seat.getBooked()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Seat is already booked");
		

		BookingEntity booking = bookingRepository.save(new BookingEntity(body));

		seat.setBooked(true);
		seat.setBookingId(booking.getBookingId());

		return booking.getBookingId();
	}

	@Transactional 
	public void initializeEvent(Long eventId, List<String> seatNames) {
		if (eventId == null || eventId < 1) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST,
				"A valid event ID is required"
			);
		}

		if (seatNames == null || seatNames.isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"At least one seat is required"
			);
		}

		List<String> normalizedSeatNames = seatNames.stream()
			.map(String::trim)
			.toList();

		if (normalizedSeatNames.stream().anyMatch(String::isBlank)) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Seat names cannot be blank"
			);
		}

		if (normalizedSeatNames.stream().distinct().count()	!= normalizedSeatNames.size()) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Seat names must be unique"
			);
		}

		 if (seatRepository.existsByEventId(eventId)) {
			throw new ResponseStatusException(
				HttpStatus.CONFLICT,
				"Event already initialized."
			);
		}

		List<SeatEntity> seats = normalizedSeatNames
			.stream()
			.map(seatName -> new SeatEntity(eventId, seatName))
			.toList();

		seatRepository.saveAll(seats);
	}

	public boolean seatIsBooked(Long eventId, String seatName) {
		SeatEntity seat = seatRepository
			.findById(new SeatId(eventId, seatName))
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Seat not found"
			));

		return seat.getBooked();
	}
}
