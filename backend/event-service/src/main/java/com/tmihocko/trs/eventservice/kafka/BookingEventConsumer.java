package com.tmihocko.trs.eventservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.tmihocko.trs.eventservice.EventService;
import com.tmihocko.trs.contracts.BookingEvent;

@Component 
public class BookingEventConsumer {
	private final EventService eventService;

	public BookingEventConsumer(EventService eventService) {
		this.eventService = eventService;
	}

	@KafkaListener(topics = "${app.kafka.booking-events-topic}")
	public void consume(BookingEvent event) {
		eventService.applyBookingEvent(event);
	}
	
}
