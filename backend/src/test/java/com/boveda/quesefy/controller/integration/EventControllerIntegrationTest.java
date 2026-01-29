package com.boveda.quesefy.controller.integration;

import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.entity.Event;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.service.EventService;
import com.boveda.quesefy.utils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static com.boveda.quesefy.utils.TestDataFactory.EVENT_TITLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateEventAndReturn201() throws Exception {
        CreateEventRequest request = TestDataFactory.createEventRequest();
        Event event = TestDataFactory.createEvent(UUID.randomUUID());

        when(eventService.createEvent(any(CreateEventRequest.class)))
                    .thenReturn(event);

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(EVENT_TITLE));
    }

    @Test
    void shouldReturnListOfEvents() throws Exception {
        Event event1 = TestDataFactory.createEvent(UUID.randomUUID());
        Event event2 = TestDataFactory.createEvent(UUID.randomUUID());

        when(eventService.listEvents()).thenReturn(List.of(event1, event2));

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(event1.getId().toString()))
                .andExpect(jsonPath("$[1].id").value(event2.getId().toString()));
    }

    @Test
    void shouldReturn200WhenEventsExist() throws Exception {
        UUID eventId = UUID.randomUUID();
        Event event = TestDataFactory.createEvent(eventId);

        when(eventService.getEventById(event.getId())).thenReturn(event);

        mockMvc.perform(get("/api/v1/events/" + event.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId().toString()));
    }

    @Test
    void shouldUpdateEventAndReturn200() throws Exception {
        UpdateEventRequest updateEventRequest = TestDataFactory.createUpdateEventRequest();

        UUID eventId = UUID.randomUUID();
        Event event = TestDataFactory.createEvent(eventId);

        when(eventService.updateEvent(event.getId(), updateEventRequest)).thenReturn(event);

        mockMvc.perform(put("/api/v1/events/" + event.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEventRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId().toString()))
                .andExpect(jsonPath("$.status").value(event.getStatus().toString()));

    }

    @Test
    void shouldReturnListOfEventsWithAndWithoutVenue() throws Exception {
        UUID venueId = UUID.randomUUID();
        Venue venue = TestDataFactory.createVenue(venueId);

        UUID eventWithVenueId = UUID.randomUUID();
        Event eventWithVenue = TestDataFactory.createEventWithVenue(eventWithVenueId, venue);

        UUID eventWithoutVenueId = UUID.randomUUID();
        Event eventWithoutVenue = TestDataFactory.createEvent(eventWithoutVenueId);

        when(eventService.listEvents()).thenReturn(List.of(eventWithVenue, eventWithoutVenue));

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].venue").exists())
                .andExpect(jsonPath("$[1].venue").doesNotExist());
    }
}
