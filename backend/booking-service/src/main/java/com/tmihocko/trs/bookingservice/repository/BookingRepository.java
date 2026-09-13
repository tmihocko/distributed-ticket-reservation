package com.tmihocko.trs.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmihocko.trs.bookingservice.entity.BookingEntity;


public interface BookingRepository extends JpaRepository<BookingEntity, Long> {


	void deleteByEventId(Long eventId);

}
