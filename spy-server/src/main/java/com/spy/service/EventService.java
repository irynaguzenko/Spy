package com.spy.service;

import com.spy.dto.EventDto;
import com.spy.exception.EventNotFoundException;
import com.spy.model.CustomUserDetails;
import com.spy.model.Event;
import com.spy.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@Validated
public class EventService {
    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Mono<Event> create(@Valid EventDto eventDto, CustomUserDetails userDetails) {
        Event event = event(eventDto);
        event.setAdmin(userDetails.getUser());
        return eventRepository.save(event)
                .doOnNext(e -> log.info("Event is created: {}", e));
    }

    public Mono<Event> join(BigInteger eventId, CustomUserDetails userDetails) {
        return eventRepository.findById(eventId)
                .flatMap(event -> {
                    event.addParticipant(userDetails.getUser());
                    return eventRepository.save(event);
                })
                .switchIfEmpty(Mono.error(new EventNotFoundException()));
    }

    private Event event(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setOpeningPeriodBeforeStartInMin(eventDto.getOpeningPeriodBeforeStartInMin());
//        event.setLocation(eventDto.getLocation());
        return event;
    }
}
