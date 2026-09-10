package com.tmihocko.trs.eventservice.dto;

import com.tmihocko.trs.eventservice.entity.EventEntity;

public class EventInfo {
	

	public static EventInfo from(EventEntity event) {
		return new EventInfo();
	}
}
