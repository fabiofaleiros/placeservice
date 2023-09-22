package com.ffs.placeservice.util;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.ffs.placeservice.domain.Place;

public class QueryBuilder {

	private QueryBuilder() {
	}

	public static Example<Place> makeQuery(Place place) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
		return Example.of(place, exampleMatcher);
	}

}
