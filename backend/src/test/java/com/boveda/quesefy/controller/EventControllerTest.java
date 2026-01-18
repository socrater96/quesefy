package com.boveda.quesefy.controller;


import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.dto.CreateEventRequestDto;
import com.boveda.quesefy.domain.dto.EventDto;
import com.boveda.quesefy.domain.dto.UpdateEventRequestDto;
import com.boveda.quesefy.domain.dto.VenueDto;
import com.boveda.quesefy.domain.entity.*;
import com.boveda.quesefy.domain.exception.EventNotFoundException;
import com.boveda.quesefy.mapper.EventMapper;
import com.boveda.quesefy.mapper.VenueMapper;
import com.boveda.quesefy.service.EventService;
import com.boveda.quesefy.utils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private EventMapper eventMapper;

    @MockitoBean
    private VenueMapper venueMapper;


    @Test
    public void shouldCreateEventAndReturn201() throws Exception {

        LocalDateTime validDate = LocalDateTime.now().plusDays(1);
        String validDateStr = validDate.toString();

        String requestJson = """
        {
          "title": "Concierto",
          "description": "Rock en vivo",
          "type": "CONCERT",
          "status": "DUE",
          "date": "%s"
        }
        """.formatted(validDateStr);

        CreateEventRequest domainRequest = new CreateEventRequest(
                "Concierto",
                "Rock en vivo",
                validDate,
                EventType.CONCERT

        );

        Event event = new Event(
                UUID.randomUUID(),
                "Concierto",
                "Rock en vivo",
                validDate,
                EventType.CONCERT,
                EventStatus.DUE

        );

        EventDto responseDto = new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getType(),
                event.getStatus(),
                null

        );

        when(eventMapper.fromDto(any(CreateEventRequestDto.class))).thenReturn(domainRequest);
        when(eventService.createEvent(domainRequest)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Concierto"))
                .andExpect(jsonPath("$.type").value("CONCERT"));

    }

    @Test
    void shouldReturn400WhenTitleIsMissing() throws Exception {

        String validDateStr = LocalDateTime.now().plusDays(1).toString();

        String invalidJson = """
        {
          "description": "Sin título",
          "type": "CONCERT",
          "status": "DUE",
          "date": "%s"
        }
        """.formatted(validDateStr);

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenDateIsInvalid() throws Exception {
        String invalidJson = """
        {
          "title": "Concierto",
          "description": "Rock en vivo",
          "type": "CONCERT",
          "status": "DUE",
          "date": "invalid-date"
        }
        """;

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400WhenDateIsPast() throws Exception {
        String pastDateStr = LocalDate.now().minusDays(1).toString();
        String invalidJson = """
        {
          "title": "Concierto",
          "description": "Rock en vivo",
          "type": "CONCERT",
          "status": "DUE",
          "date": "%s"
        }
        """.formatted(pastDateStr);

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnListOfEvents() throws Exception {
        LocalDateTime validDate = LocalDateTime.now().plusDays(1);

        Event event1 = new Event(
                UUID.randomUUID(),
                "Concierto",
                "Rock en vivo",
                validDate,
                EventType.CONCERT,
                EventStatus.DUE

        );
        Event event2 = new Event(
                UUID.randomUUID(),
                "One Battle After Another",
                "Película de PT Anderson con Leonardo Dicaprio",
                validDate,
                EventType.MOVIE,
                EventStatus.DUE
        );

        when(eventService.listEvents()).thenReturn(List.of(event1, event2));
        when(eventMapper.toDto(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event e = invocation.getArgument(0);
                    return new EventDto(
                            e.getId(),
                            e.getTitle(),
                            e.getDescription(),
                            e.getDate(),
                            e.getType(),
                            e.getStatus(),
                            null
                    );
                });
        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(event1.getId().toString()))
                .andExpect(jsonPath("$[1].type").value("MOVIE"));
    }

    @Test
    void shouldReturnEmptyListWhenNoEventsExist() throws Exception {

        when(eventService.listEvents()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturn200WhenEventsExist() throws Exception {
        LocalDateTime validDate = LocalDateTime.now().plusDays(1);

        Event event = new Event(
                UUID.randomUUID(),
                "Concierto",
                "Rock en vivo",
                validDate,
                EventType.CONCERT,
                EventStatus.DUE

        );

        EventDto eventDto = new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getType(),
                event.getStatus(),
                null
        );

        when(eventService.getEventById(event.getId())).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(eventDto);

        mockMvc.perform(get("/api/v1/events/" + event.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId().toString()));
    }

    @Test
    void shouldReturn404WhenEventDoesNotExist() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        when(eventService.getEventById(nonExistingId))
                .thenThrow(new EventNotFoundException(nonExistingId));

        mockMvc.perform(get("/api/v1/events/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateEventAndReturn200() throws Exception {
        LocalDateTime validDate = LocalDateTime.now().plusDays(1);
        String validDateStr = validDate.toString();

        String requestJson = """
        {
          "title": "Concierto",
          "description": "Rock en vivo",
          "type": "CONCERT",
          "status": "DUE",
          "date": "%s"
        }
        """.formatted(validDateStr);

        UpdateEventRequest domainRequest = new UpdateEventRequest(
                "Concierto",
                "Rock en vivo",
                validDate,
                EventType.CONCERT,
                EventStatus.CANCELED

        );

        Event event = new Event(
                UUID.randomUUID(),
                "Concierto",
                "Rock en vivo",
                validDate,
                EventType.CONCERT,
                EventStatus.CANCELED

        );

        EventDto responseDto = new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getType(),
                event.getStatus(),
                null

        );

        when(eventMapper.fromDto(any(UpdateEventRequestDto.class))).thenReturn(domainRequest);
        when(eventService.updateEvent(event.getId(), domainRequest)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/events/" + event.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId().toString()))
                .andExpect(jsonPath("$.status").value(event.getStatus().toString()));

    }

    @Test
    void shouldUpdateEventAndReturn404WhenEventDoesNotExist() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        LocalDateTime validDate = LocalDateTime.now().plusDays(1);

        String requestJson = """
        {
          "title": "Concierto",
          "description": "Rock en vivo",
          "type": "CONCERT",
          "status": "DUE",
          "date": "%s"
        }
        """.formatted(validDate.toString());

        UpdateEventRequest domainRequest = new UpdateEventRequest(
                "Concierto",
                "Rock en vivo",
                validDate,
                EventType.CONCERT,
                EventStatus.DUE
        );
        when(eventMapper.fromDto(any(UpdateEventRequestDto.class)))
                .thenReturn(domainRequest);
        when(eventService.updateEvent(nonExistingId, domainRequest))
                .thenThrow(new EventNotFoundException(nonExistingId));

        mockMvc.perform(put("/api/v1/events/" + nonExistingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnListOfEventsWithAndWithoutVenue() throws Exception {
        LocalDateTime validDate = LocalDateTime.now().plusDays(1);

        Venue venue = new Venue(
                UUID.randomUUID(),
                "Folks",
                VenueType.NIGHT_CLUB,
                TestDataFactory.createLocation()
        );

        Event event1 = new Event(
                UUID.randomUUID(),
                "Concierto",
                "Rock",
                LocalDateTime.now().plusDays(1),
                EventType.CONCERT,
                EventStatus.DUE
        );

        event1.assignVenue(venue);

        VenueDto venueDto = new VenueDto(
                venue.getId(),
                venue.getName(),
                venue.getVenueType(),
                TestDataFactory.createLocationDto()
        );

        Event event2 = new Event(
                UUID.randomUUID(),
                "One Battle After Another",
                "Película de PT Anderson con Leonardo Dicaprio",
                validDate,
                EventType.MOVIE,
                EventStatus.DUE
        );

        when(venueMapper.toDto(any(Venue.class))).thenReturn(venueDto);
        when(eventService.listEvents()).thenReturn(List.of(event1, event2));
        when(eventMapper.toDto(any(Event.class)))
                .thenAnswer(invocation -> {
            Event e = invocation.getArgument(0);
            return new EventDto(
                    e.getId(),
                    e.getTitle(),
                    e.getDescription(),
                    e.getDate(),
                    e.getType(),
                    e.getStatus(),
                    e.getVenue() != null ? venueDto : null
            );
        });

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(event1.getId().toString()))
                .andExpect(jsonPath("$[1].id").value(event2.getId().toString()))
                .andExpect(jsonPath("$[0].venue").exists())
                .andExpect(jsonPath("$[0].venue.name").value("Folks"))
                .andExpect(jsonPath("$[0].venue.venueType").value("NIGHT_CLUB"))
                .andExpect(jsonPath("$[0].venue.location.city").value("A Coruña"))
                .andExpect(jsonPath("$[0].venue.location.latitude").value(43.371909))
                .andExpect(jsonPath("$[0].venue.location.longitude").value(-8.400471))
                .andExpect(jsonPath("$[1].venue").isEmpty());
    }


}
