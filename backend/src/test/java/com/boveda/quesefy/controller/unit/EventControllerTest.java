package com.boveda.quesefy.controller.unit;

import com.boveda.quesefy.controller.EventController;
import com.boveda.quesefy.domain.UpdateEventRequest;
import com.boveda.quesefy.domain.dto.UpdateEventRequestDto;
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
    void shouldReturnEmptyListWhenNoEventsExist() throws Exception {

        when(eventService.listEvents()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
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

}
