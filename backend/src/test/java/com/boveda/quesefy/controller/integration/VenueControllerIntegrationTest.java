package com.boveda.quesefy.controller.integration;

import com.boveda.quesefy.domain.CreateVenueRequest;
import com.boveda.quesefy.domain.UpdateVenueRequest;
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

import java.util.List;
import java.util.UUID;

import static com.boveda.quesefy.utils.TestDataFactory.VENUE_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Test
    void shouldReturnEmptyListWhenNoVenues() throws Exception {
        when(venueService.listVenues()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/venues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnListOfVenues() throws Exception {
        Venue venue1 = TestDataFactory.createVenue(UUID.randomUUID());
        Venue venue2 = TestDataFactory.createVenue(UUID.randomUUID());

        when(venueService.listVenues()).thenReturn(List.of(venue1, venue2));

        mockMvc.perform((get("/api/v1/venues")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(venue1.getId().toString()))
                .andExpect(jsonPath("$[1].id").value(venue2.getId().toString()));
    }

    @Test
    void shouldReturn200WhenVenueExist() throws Exception {
        UUID venueId = UUID.randomUUID();
        Venue venue = TestDataFactory.createVenue(venueId);

        when(venueService.getById(venue.getId())).thenReturn(venue);

        mockMvc.perform(get("/api/v1/venues/" + venue.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(venue.getId().toString()));
    }

    @Test
    void shouldUpdateVenueAndReturn200() throws Exception {
        UpdateVenueRequest domainRequest = TestDataFactory.createUpdateVenueRequest();

        UUID venueId = UUID.randomUUID();
        Venue venue = TestDataFactory.createVenue(venueId);
        venue.setName(domainRequest.name());

        when(venueService.update(
                eq(venueId),
                any(UpdateVenueRequest.class)
        )).thenReturn(venue);

        mockMvc.perform(put("/api/v1/venues/" + venue.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(domainRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(venue.getId().toString()))
                .andExpect(jsonPath("$.name").value(venue.getName()));
    }
}
