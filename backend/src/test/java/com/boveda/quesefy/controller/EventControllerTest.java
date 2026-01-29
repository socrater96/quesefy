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
import tools.jackson.databind.ObjectMapper;

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

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private EventMapper eventMapper;

    @MockitoBean
    private VenueMapper venueMapper;


    @Test
    public void shouldCreateEventAndReturn201() throws Exception {
        CreateEventRequest request = TestDataFactory.createEventRequest();

        UUID eventId = UUID.randomUUID();
        Event event = TestDataFactory.createEvent(eventId);

        EventDto responseDto = TestDataFactory.createEventDto(event.getId());

        when(eventMapper.fromDto(any(CreateEventRequestDto.class))).thenReturn(request);
        when(eventService.createEvent(request)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("DJ Chvrly"))
                .andExpect(jsonPath("$.type").value("CONCERT"));

    }

    @Test
    void shouldReturn400WhenTitleIsMissing() throws Exception {
        String validDateStr = LocalDateTime.of(
                1970, 1, 1, 0, 0, 0
                )
                .toString();


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
        UUID eventId1 = UUID.randomUUID();
        Event event1 = TestDataFactory.createEvent(eventId1);

        UUID eventId2 = UUID.randomUUID();
        Event event2 = TestDataFactory.createEvent(eventId2);

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
                .andExpect(jsonPath("$[1].id").value(event2.getId().toString()));
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
        UUID eventId = UUID.randomUUID();
        Event event = TestDataFactory.createEvent(eventId);

        EventDto eventDto = TestDataFactory.createEventDto(event.getId());

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
        UpdateEventRequest updateEventRequest = TestDataFactory.createUpdateEventRequest();

        UUID eventId = UUID.randomUUID();
        Event event = TestDataFactory.createEvent(eventId);

        EventDto eventDto = TestDataFactory.createEventDto(event.getId());

        when(eventMapper.fromDto(any(UpdateEventRequestDto.class))).thenReturn(updateEventRequest);
        when(eventService.updateEvent(event.getId(), updateEventRequest)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(eventDto);

        mockMvc.perform(put("/api/v1/events/" + event.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEventRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId().toString()))
                .andExpect(jsonPath("$.status").value(event.getStatus().toString()));

    }

    @Test
    void shouldNotUpdateEventAndReturn404WhenEventDoesNotExist() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        UpdateEventRequest updateEventRequest = TestDataFactory.createUpdateEventRequest();

        when(eventMapper.fromDto(any(UpdateEventRequestDto.class)))
                .thenReturn(updateEventRequest);
        when(eventService.updateEvent(nonExistingId, updateEventRequest))
                .thenThrow(new EventNotFoundException(nonExistingId));

        mockMvc.perform(put("/api/v1/events/" + nonExistingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateEventRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnListOfEventsWithAndWithoutVenue() throws Exception {
        UUID venueId = UUID.randomUUID();
        Venue venue = TestDataFactory.createVenue(venueId);

        UUID eventWithVenueId = UUID.randomUUID();
        Event eventWithVenue = TestDataFactory.createEventWithVenue(eventWithVenueId, venue);

        VenueDto venueDto = TestDataFactory.createVenueDto(venue.getId());

        UUID eventWithoutVenueId = UUID.randomUUID();
        Event eventWithoutVenue = TestDataFactory.createEvent(eventWithoutVenueId);

        when(venueMapper.toDto(any(Venue.class))).thenReturn(venueDto);
        when(eventService.listEvents()).thenReturn(List.of(eventWithVenue, eventWithoutVenue));
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
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].venue").exists())
                .andExpect(jsonPath("$[1].venue").doesNotExist());
    }


}
