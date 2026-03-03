package com.boveda.quesefy.controller.unit;

import com.boveda.quesefy.config.SecurityConfig;
import com.boveda.quesefy.controller.EventController;
import com.boveda.quesefy.domain.CreateEventRequest;
import com.boveda.quesefy.domain.dto.CreateEventRequestDto;
import com.boveda.quesefy.domain.entity.Event;
import com.boveda.quesefy.domain.entity.EventType;
import com.boveda.quesefy.mapper.EventMapper;
import com.boveda.quesefy.service.EventService;
import com.boveda.quesefy.utils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@Import(SecurityConfig.class)
@ImportAutoConfiguration({
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        ServletWebSecurityAutoConfiguration.class
})
@TestPropertySource(properties = {
        "quesefy.security.admin.username=admin",
        "quesefy.security.admin.password=admin123",
        "quesefy.security.user.username=user",
        "quesefy.security.user.password=user123"
})
public class EventAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private EventMapper eventMapper;

    @Test
    void shouldAllowAnonymousUserToListEvents() throws Exception {
        when(eventService.listEvents()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldAllowUserToListEvents() throws Exception {
        when(eventService.listEvents()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/events")
                        .with(httpBasic("user", "user123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturn403WhenUserCreatesEvent() throws Exception {
        mockMvc.perform(post("/api/v1/events")
                        .with(httpBasic("user", "user123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCreateEventRequestDto())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Access denied"));
    }

    @Test
    void shouldAllowAdminToCreateEvent() throws Exception {
        UUID eventId = UUID.randomUUID();
        CreateEventRequest createEventRequest = TestDataFactory.createEventRequest();
        Event event = TestDataFactory.createEvent(eventId);

        when(eventMapper.fromDto(any(CreateEventRequestDto.class))).thenReturn(createEventRequest);
        when(eventService.createEvent(createEventRequest)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(TestDataFactory.createEventDto(eventId));

        mockMvc.perform(post("/api/v1/events")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCreateEventRequestDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(eventId.toString()))
                .andExpect(jsonPath("$.title").value(TestDataFactory.EVENT_TITLE));
    }

    private CreateEventRequestDto createCreateEventRequestDto() {
        return new CreateEventRequestDto(
                TestDataFactory.EVENT_TITLE,
                TestDataFactory.EVENT_DESCRIPTION,
                LocalDateTime.of(2030, 1, 1, 20, 0),
                EventType.CONCERT,
                null
        );
    }
}
