package com.boveda.quesefy.controller.unit;

import com.boveda.quesefy.controller.VenueController;
import com.boveda.quesefy.domain.exception.VenueNotFoundException;
import com.boveda.quesefy.mapper.VenueMapper;
import com.boveda.quesefy.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VenueController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VenueMapper venueMapper;

    @MockitoBean
    private VenueService venueService;

    @Test
    void shouldReturn404whenVenueNotFound() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        when(venueService.getById(nonExistingId))
                .thenThrow(new VenueNotFoundException(nonExistingId));

        mockMvc.perform(get("/api/v1/venues/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

}
