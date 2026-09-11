package com.tmihocko.trs.eventservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.tmihocko.trs.eventservice.entity.EventEntity;

@Component 
public class EventClient {
	
	private final RestClient restClient;

	public EventClient(RestClient.Builder builder, @Value("${services.booking.base-url}") String baseUrl) {
		this.restClient = builder
			.baseUrl(baseUrl)
			.build();
	}
	// REST response
	// Should always be synchronous
	public HttpStatusCode postEventToBooking(EventEntity event, List<String> seatNames) {
		var request = new BookingEventRequest(event.getEventId(), seatNames);

		return restClient.post()
			.uri("/internal/events")
			.contentType(MediaType.APPLICATION_JSON)
			.body(request)
			.exchange((httpRequest, httpResponse) -> httpResponse.getStatusCode());
	}

	private record BookingEventRequest(
            Long eventId,
            List<String> seatNames
    ) {}
}
