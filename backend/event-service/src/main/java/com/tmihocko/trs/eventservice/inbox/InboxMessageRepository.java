package com.tmihocko.trs.eventservice.inbox;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxMessageRepository extends JpaRepository<InboxMessageEntity, UUID> {
	
}
