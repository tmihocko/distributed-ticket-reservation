package com.tmihocko.trs.bookingservice.kafka;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.tmihocko.trs.contracts.BookingEvent;


@Component 
public class BookingEventProducer {
	
	private final KafkaTemplate<Long, BookingEvent> kafkaTemplate;
	private final String topic;


	public BookingEventProducer(
		KafkaTemplate<Long, BookingEvent> kafkaTemplate, 
		@Value("${app.kafka.booking-events-topic}") String topic
	) {
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}

	public CompletableFuture<SendResult<Long, BookingEvent>> send(BookingEvent event) {
		return kafkaTemplate.send(topic, event.eventId(), event);
	}
}
