package com.ffs.placeservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.ffs.placeservice.api.PlaceRequest;
import com.ffs.placeservice.domain.Place;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlaceServiceTests {

	public static final Place CENTRAL_PARK = new Place(1L, "Central Park", "central-park", "NY", "NY", null, null);

	@Autowired
	private WebTestClient webTestClient;

	@Order(1)
	@Test
	public void testCreatePlaceSucess() {
		final String name = "Valid Name";
		final String city = "Valid City";
		final String state = "Valid State";
		final String slug = "valid-name";

		webTestClient
				.post()
				.uri("/places")
				.bodyValue(new PlaceRequest(name, city, state))
				.exchange()
				.expectBody()
				.jsonPath("name").isEqualTo(name)
				.jsonPath("city").isEqualTo(city)
				.jsonPath("state").isEqualTo(state)
				.jsonPath("slug").isEqualTo(slug)
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();
	}

	@Order(2)
	@Test
	public void testCreatePlaceFailure() {
		final String name = "";
		final String state = "";
		final String city = "";

		webTestClient
			.post()
			.uri("/places")
			.bodyValue(new PlaceRequest(name, city, state))
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Order(3)
	@Test
	public void testEditPlaceSuccess() {
		final String newName = "New Name";
		final String newCity = "New City";
		final String newState = "New State";
		final String newSlug = "new-name";

		// Updates name, city and state.
		webTestClient
			.patch()
			.uri("/places/1")
			.bodyValue(new PlaceRequest(newName, newCity, newState))
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("name").isEqualTo(newName)
			.jsonPath("city").isEqualTo(newCity)
			.jsonPath("state").isEqualTo(newState)
			.jsonPath("slug").isEqualTo(newSlug)
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty();

		// Updates only name
		webTestClient
			.patch()
			.uri("/places/1")
			.bodyValue(new PlaceRequest(CENTRAL_PARK.name(), null, null))
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
			.jsonPath("city").isEqualTo(newCity)
			.jsonPath("state").isEqualTo(newState)
			.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty();

		// Updates only city
		webTestClient
			.patch()
			.uri("/places/1")
			.bodyValue(new PlaceRequest(null, CENTRAL_PARK.city(), null))
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
			.jsonPath("city").isEqualTo(CENTRAL_PARK.city())
			.jsonPath("state").isEqualTo(newState)
			.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty();

		// Updates only state
		webTestClient
			.patch()
			.uri("/places/1")
			.bodyValue(new PlaceRequest(null, null, CENTRAL_PARK.state()))
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
			.jsonPath("city").isEqualTo(CENTRAL_PARK.city())
			.jsonPath("state").isEqualTo(CENTRAL_PARK.state())
			.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty();
	}

	@Order(4)
	@Test
	public void testGetSuccess() {
		webTestClient
			.get()
			.uri("/places/1")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
			.jsonPath("state").isEqualTo(CENTRAL_PARK.state())
			.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty();
	}

	@Order(5)
	@Test
	public void testGetFailure() {
		webTestClient
			.get()
			.uri("/places/11")
			.exchange()
			.expectStatus().isNotFound();
	}

	@Order(6)
	@Test
	public void testListAllSuccess() {
		webTestClient
			.get()
			.uri("/places")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$[0].name").isEqualTo(CENTRAL_PARK.name())
			.jsonPath("$[0].slug").isEqualTo(CENTRAL_PARK.slug())
			.jsonPath("$[0].state").isEqualTo(CENTRAL_PARK.state())
			.jsonPath("$[0].createdAt").isNotEmpty()
			.jsonPath("$[0].updatedAt").isNotEmpty();
	}

	@Order(7)
	@Test
	public void testListByNameSuccess() {
		webTestClient
			.get()
			.uri("/places?name=%s".formatted(CENTRAL_PARK.name()))
			.exchange().expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(1)
			.jsonPath("$[0].name").isEqualTo(CENTRAL_PARK.name())
			.jsonPath("$[0].slug").isEqualTo(CENTRAL_PARK.slug())
			.jsonPath("$[0].state").isEqualTo(CENTRAL_PARK.state())
			.jsonPath("$[0].createdAt").isNotEmpty()
			.jsonPath("$[0].updatedAt").isNotEmpty();
	}

	@Order(8)
	@Test
	public void testListByNameNotFound() {
		webTestClient
			.get()
			.uri("/places?name=name")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(0);
	}

}
