package com.boveda.quesefy.controller.integration;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.entity.Venue;
import com.boveda.quesefy.service.VenueService;
import com.boveda.quesefy.utils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static com.boveda.quesefy.utils.TestDataFactory.VENUE_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VenueControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VenueService venueService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateVenueAndReturn201() throws Exception {
        CreateVenueRequest domainRequest = TestDataFactory.createCreateVenueRequest();

        Venue venue = TestDataFactory.createVenue(UUID.randomUUID());

        when(venueService.createVenue(any(CreateVenueRequest.class)))
                .thenReturn(venue);

        mockMvc.perform((post("/api/v1/venues"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(domainRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(VENUE_NAME));
    }
}
