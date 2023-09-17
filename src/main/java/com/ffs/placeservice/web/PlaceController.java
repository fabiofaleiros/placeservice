package com.ffs.placeservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ffs.placeservice.domain.Place;
import com.ffs.placeservice.domain.PlaceService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {
	
	private PlaceService placeService;
	
	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}
	
	public ResponseEntity<Mono<Place>> create(Place place) {
		var placeResponse = placeService.create(place);
		return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
	}

}
