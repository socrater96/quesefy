package com.boveda.quesefy.e2e;

import com.boveda.quesefy.domain.dto.EventDto;
import com.boveda.quesefy.domain.dto.VenueDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SeededCatalogE2ETest extends BaseE2ETest {

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Test
    void shouldExposeSeededVenuesFromPostgres() throws Exception {
        HttpResponse<String> response = sendGet("/api/v1/venues");
        VenueDto[] venues = objectMapper.readValue(response.body(), VenueDto[].class);

        assertEquals(200, response.statusCode());
        assertNotNull(venues);
        assertEquals(3, venues.length);

        Map<UUID, VenueDto> venuesById = List.of(venues).stream()
                .collect(Collectors.toMap(VenueDto::id, Function.identity()));

        VenueDto salaAtlantica = venuesById.get(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        assertNotNull(salaAtlantica);
        assertEquals("Sala Atlantica", salaAtlantica.name());
        assertEquals("A Coruna", salaAtlantica.location().city());

        VenueDto teatroCentral = venuesById.get(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        assertNotNull(teatroCentral);
        assertEquals("Teatro Central", teatroCentral.name());
        assertEquals("Valencia", teatroCentral.location().city());
    }

    @Test
    void shouldExposeSeededEventsFromPostgres() throws Exception {
        HttpResponse<String> response = sendGet("/api/v1/events");
        EventDto[] events = objectMapper.readValue(response.body(), EventDto[].class);

        assertEquals(200, response.statusCode());
        assertNotNull(events);
        assertEquals(4, events.length);

        Map<UUID, EventDto> eventsById = List.of(events).stream()
                .collect(Collectors.toMap(EventDto::id, Function.identity()));

        EventDto indieNights = eventsById.get(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        assertNotNull(indieNights);
        assertEquals("Indie Nights", indieNights.title());
        assertEquals(UUID.fromString("11111111-1111-1111-1111-111111111111"), indieNights.venueId());

        EventDto cityHistoryTalk = eventsById.get(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"));
        assertNotNull(cityHistoryTalk);
        assertEquals("City History Talk", cityHistoryTalk.title());
        assertEquals(UUID.fromString("33333333-3333-3333-3333-333333333333"), cityHistoryTalk.venueId());
    }

    private HttpResponse<String> sendGet(String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
