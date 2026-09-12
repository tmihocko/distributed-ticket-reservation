package com.tmihocko.trs.bookingservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmihocko.trs.bookingservice.entity.SeatEntity;
import com.tmihocko.trs.bookingservice.entity.SeatId;

import jakarta.persistence.LockModeType;


public interface SeatRepository extends JpaRepository<SeatEntity, SeatId> {


	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
		SELECT s
		FROM SeatEntity s
		WHERE s.eventId = :eventId
			AND s.seatName = :seatName
	""")
	Optional<SeatEntity> findForBooking(
		@Param("eventId") Long eventId,
		@Param("seatName") String seatName
	);

	boolean existsByEventId(Long eventId);
}
