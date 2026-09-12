package com.tmihocko.trs.eventservice.dto;

import java.time.OffsetDateTime;
import java.util.List;


public record PostEvent(
	String name, 
	String venueName,
	OffsetDateTime date, 
	Integer capacity, 
	List<String> seatNames
) {}
