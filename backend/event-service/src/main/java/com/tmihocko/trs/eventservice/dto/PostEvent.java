package com.tmihocko.trs.eventservice.dto;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record PostEvent(
	@NotBlank
	@Size(max=200) 
	String name, 
	
	@NotBlank
	@Size(max = 200)
	String venueName,

	@NotNull 
	@Future 
	OffsetDateTime date, 

	@NotNull 
	@Positive 
	Integer capacity, 

	List<@NotBlank String> seatNames
) {}
