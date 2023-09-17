package com.ffs.placeservice.domain;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class PlaceService {
	
	private PlaceRepository placeRepository;
	
	public PlaceService(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}
	
	public Mono<Place> create(Place place) {
		
		return placeRepository.save(place);
		
	}

}
