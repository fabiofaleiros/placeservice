package com.ffs.placeservice.api;

import jakarta.validation.constraints.NotBlank;

public record PlaceRequest(@NotBlank String name, @NotBlank String city, @NotBlank String state) {

}
