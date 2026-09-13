package com.tmihocko.trs.bookingservice.outbox;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<OutboxEventEntity> findByPublishedAtIsNullOrderByOccurredAtAsc(Pageable pageable);

}
