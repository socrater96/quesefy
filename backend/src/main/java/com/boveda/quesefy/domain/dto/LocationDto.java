package com.boveda.quesefy.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LocationDto(
        @Schema(example = "Kensington Gore")
        @NotNull String address,

        @Schema(example = "London")
        @NotNull String city,

        @Schema(example = "Greater London")
        @NotNull String province,

        @Schema(example = "SW7 2AP")
        @NotNull String zipcode,

        @Schema(example = "UK")
        @NotNull String country,


        @Schema(example = "51.5010")
        @NotNull Double latitude,

        @Schema(example = "-0.1773")
        @NotNull Double longitude
) {
}
