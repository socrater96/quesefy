--
-- PostgreSQL database dump
--

-- Dumped from database version 16.12 (Debian 16.12-1.pgdg13+1)
-- Dumped by pg_dump version 16.12 (Debian 16.12-1.pgdg13+1)

--
-- Name: events; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.events (
    date timestamp(6) without time zone,
    id uuid NOT NULL,
    venue_id uuid,
    description character varying(1000),
    status character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT events_status_check CHECK (((status)::text = ANY ((ARRAY['DUE'::character varying, 'COMPLETED'::character varying, 'CANCELED'::character varying])::text[]))),
    CONSTRAINT events_type_check CHECK (((type)::text = ANY ((ARRAY['CONCERT'::character varying, 'MOVIE'::character varying, 'PLAY'::character varying, 'LECTURE'::character varying])::text[])))
);


--
-- Name: venues; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.venues (
    latitude double precision,
    longitude double precision,
    id uuid NOT NULL,
    address character varying(255),
    city character varying(255),
    country character varying(255),
    name character varying(255) NOT NULL,
    province character varying(255),
    venue_type character varying(255) NOT NULL,
    zipcode character varying(255),
    CONSTRAINT venues_venue_type_check CHECK (((venue_type)::text = ANY ((ARRAY['CONCERT_HALL'::character varying, 'THEATER'::character varying, 'MOVIE_THEATER'::character varying, 'SPORTS_CENTRE'::character varying, 'NIGHT_CLUB'::character varying, 'MUSIC_VENUE'::character varying, 'PUBLIC_SQUARE'::character varying])::text[])))
);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey
    PRIMARY KEY (id);


--
-- Name: venues venues_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venues
    ADD CONSTRAINT venues_pkey
    PRIMARY KEY (id);


--
-- Name: events fkqdxygdernwwt74hdvix9u5nr3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fkqdxygdernwwt74hdvix9u5nr3
    FOREIGN KEY (venue_id)
    REFERENCES public.venues(id);

CREATE INDEX idx_events_venue_id ON public.events (venue_id);

--
-- PostgreSQL database dump complete
--


