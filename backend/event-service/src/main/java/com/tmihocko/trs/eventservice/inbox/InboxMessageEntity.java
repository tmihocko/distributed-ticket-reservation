package com.tmihocko.trs.eventservice.inbox;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_event_inbox")
public class InboxMessageEntity {

	@Id 
	private UUID messageId;

	@Column(nullable = false)
	private Instant processedAt;

	public InboxMessageEntity(UUID messageId) {
        this.messageId = messageId;
        this.processedAt = Instant.now();
    }

    protected InboxMessageEntity() {}
}
