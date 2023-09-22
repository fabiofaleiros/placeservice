package com.ffs.placeservice.web;

import com.ffs.placeservice.api.PlaceResponse;
import com.ffs.placeservice.domain.Place;

public class PlaceMapper {
	
	public static PlaceResponse fromPlaceToResponse(Place place) {
		return new PlaceResponse(place.name(), place.slug(), place.state(), place.createdAt(), place.updatedAt());
	}

}
