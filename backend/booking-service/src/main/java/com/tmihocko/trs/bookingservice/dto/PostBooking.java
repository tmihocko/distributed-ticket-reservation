package com.tmihocko.trs.bookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PostBooking(
	@NotNull @Positive Long eventId,
	@NotBlank String seatName,
	@NotBlank String userId
) {
	
} 
