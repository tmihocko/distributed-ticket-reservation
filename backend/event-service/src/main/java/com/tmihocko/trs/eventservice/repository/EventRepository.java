package com.tmihocko.trs.eventservice.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmihocko.trs.eventservice.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {


	@Query("""
		SELECT e
		FROM EventEntity e
		WHERE (:name IS NULL OR e.name = :name)
		  AND (:date IS NULL OR e.date = :date)
		  AND (:venueId IS NULL OR e.venue.id = :venueId)
	""")
	List<EventEntity> search(
		@Param("name") String name, 
		@Param("date") OffsetDateTime date, 
		@Param("venueId") Long venueId
	);

	

}
