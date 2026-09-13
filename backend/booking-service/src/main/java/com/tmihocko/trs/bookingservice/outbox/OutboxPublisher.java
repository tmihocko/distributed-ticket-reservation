package com.tmihocko.trs.bookingservice.outbox;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tmihocko.trs.bookingservice.kafka.BookingEventProducer;
import com.tmihocko.trs.contracts.BookingEvent;

import jakarta.transaction.Transactional;

@Component 
public class OutboxPublisher {
	
	private final OutboxEventRepository outboxRepository;
	private final BookingEventProducer producer;

	public OutboxPublisher(OutboxEventRepository outboxRepository, BookingEventProducer producer) {
		this.outboxRepository = outboxRepository;
		this.producer = producer;
	}

	@Scheduled(fixedDelayString = "${app.kafka.outbox-poll-delay-ms:100}")
	@Transactional 
	public void publishPendingEvents() throws Exception {
		List<OutboxEventEntity> rows = outboxRepository
			.findByPublishedAtIsNullOrderByOccurredAtAsc(PageRequest.of(0, 500));

		if (rows.isEmpty()) return;

		CompletableFuture<?>[] sends = rows
			.stream()
			.map(this::send)
			.toArray(CompletableFuture[]::new);

		// Wait for kafka to ACK then mark rows as published
		CompletableFuture
            .allOf(sends)
            .get(10, TimeUnit.SECONDS);

        rows.forEach(OutboxEventEntity::markPublished);
    }


	private CompletableFuture<?> send(OutboxEventEntity row) {
		BookingEvent event = new BookingEvent(
			row.getMessageId(),
			row.getEventId(),
			row.getBookingType(),
			row.getBookingId(),
			row.getSeatName(),
			row.getOccurredAt()
		);

		return producer.send(event);
	}

}
