package com.tmihocko.trs.eventservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmihocko.trs.eventservice.entity.VenueEntity;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> {

    Optional<VenueEntity> findByNameIgnoreCase(String name);
}