INSERT INTO public.venues (
    id,
    name,
    venue_type,
    address,
    city,
    province,
    zipcode,
    country,
    latitude,
    longitude
) VALUES
    (
        '11111111-1111-1111-1111-111111111111',
        'Sala Atlantica',
        'MUSIC_VENUE',
        'Rua Real 12',
        'A Coruna',
        'A Coruna',
        '15003',
        'Spain',
        43.370200,
        -8.395800
    ),
    (
        '22222222-2222-2222-2222-222222222222',
        'Teatro Central',
        'THEATER',
        'Avenida del Puerto 45',
        'Valencia',
        'Valencia',
        '46024',
        'Spain',
        39.456800,
        -0.336100
    ),
    (
        '33333333-3333-3333-3333-333333333333',
        'Plaza Mayor Open Air',
        'PUBLIC_SQUARE',
        'Plaza Mayor',
        'Madrid',
        'Madrid',
        '28012',
        'Spain',
        40.415400,
        -3.707400
    );

INSERT INTO public.events (
    id,
    title,
    description,
    date,
    type,
    status,
    venue_id
) VALUES
    (
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        'Indie Nights',
        'Live indie concert with local bands.',
        '2030-07-18 21:00:00',
        'CONCERT',
        'DUE',
        '11111111-1111-1111-1111-111111111111'
    ),
    (
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        'Hamlet Revisited',
        'Modern staging of Hamlet with a resident cast.',
        '2030-09-02 20:00:00',
        'PLAY',
        'DUE',
        '22222222-2222-2222-2222-222222222222'
    ),
    (
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        'City History Talk',
        'Public lecture on urban history and architecture.',
        '2030-10-05 18:30:00',
        'LECTURE',
        'DUE',
        '33333333-3333-3333-3333-333333333333'
    ),
    (
        'dddddddd-dddd-dddd-dddd-dddddddddddd',
        'Classic Film Weekend',
        'Screening of restored European classics.',
        '2030-11-22 19:30:00',
        'MOVIE',
        'DUE',
        '22222222-2222-2222-2222-222222222222'
    );
