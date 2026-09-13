package com.tmihocko.trs.bookingservice.outbox;

import java.time.Instant;
import java.util.UUID;

import com.tmihocko.trs.contracts.BookingEvent.BookingType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_outbox")
public class OutboxEventEntity {

    @Id
    private UUID messageId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private String seatName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingType bookingType;

    @Column(nullable = false)
    private Instant occurredAt;

    private Instant publishedAt;

    public OutboxEventEntity(
        Long eventId,
        Long bookingId,
        String seatName,
        BookingType bookingType
    ) {
        this.messageId = UUID.randomUUID();
        this.eventId = eventId;
        this.bookingId = bookingId;
        this.seatName = seatName;
        this.bookingType = bookingType;
        this.occurredAt = Instant.now();
    }

    public void markPublished() {
        publishedAt = Instant.now();
    }

    public UUID getMessageId() {
        return messageId;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public String getSeatName() {
        return seatName;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    protected OutboxEventEntity() {}
}