package com.boveda.quesfy.controller;


import com.boveda.quesfy.domain.CreateEventRequest;
import com.boveda.quesfy.domain.dto.EventDto;
import com.boveda.quesfy.domain.entity.Event;
import com.boveda.quesfy.domain.entity.EventStatus;
import com.boveda.quesfy.domain.entity.EventType;
import com.boveda.quesfy.mapper.EventMapper;
import com.boveda.quesfy.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    @Test
    public void shouldCreateEventAndReturn201() throws Exception {

        LocalDate validDate = LocalDate.now().plusDays(1);
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
                event.getStatus()

        );

        when(eventMapper.fromDto(any())).thenReturn(domainRequest);
        when(eventService.createEvent(any())).thenReturn(event);
        when(eventMapper.toDto(any())).thenReturn(responseDto);

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

        String validDateStr = LocalDate.now().plusDays(1).toString();

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

}
