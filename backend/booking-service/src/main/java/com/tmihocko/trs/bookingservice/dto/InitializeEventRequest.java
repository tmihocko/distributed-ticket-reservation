package com.tmihocko.trs.bookingservice.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InitializeEventRequest(
    @NotNull @Positive Long eventId,
    @NotEmpty List<@NotBlank String> seatNames
) {}