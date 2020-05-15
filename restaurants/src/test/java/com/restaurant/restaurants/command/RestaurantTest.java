package com.restaurant.restaurants.command;

import com.restaurant.restaurants.events.RestaurantSignedUpEvent;
import com.restaurant.restaurants.command.AddAddressCommand;
import com.restaurant.restaurants.command.Restaurant;
import com.restaurant.restaurants.command.SignUpRestaurantCommand;
import com.restaurant.restaurants.events.AddressAddedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static java.util.UUID.randomUUID;

public class RestaurantTest {
    FixtureConfiguration<Restaurant> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(Restaurant.class);
    }

    @Test
    public void signUpRestaurant() throws Exception {
        String id = randomUUID().toString();

        fixture
            .givenNoPriorActivity()
            .when(new SignUpRestaurantCommand(id, "jdoe@gmail.com"))
            .expectEvents(new RestaurantSignedUpEvent(id, "jdoe@gmail.com"));
    }

    @Test
    public void signUpRestaurant_withInvalidEmail() throws Exception {
        String id = randomUUID().toString();

        fixture
            .givenNoPriorActivity()
            .when(new SignUpRestaurantCommand(id, "jdoegmail.com"))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    public void addDeliveryAddress() throws Exception {
        String restaurantId = randomUUID().toString();
        String addressId = randomUUID().toString();

        fixture
            .given(new RestaurantSignedUpEvent(restaurantId, "jdoe@gmail.com"))
            .when(new AddAddressCommand(restaurantId, addressId, "home", "555 Main St"))
            .expectEvents(new AddressAddedEvent(restaurantId, addressId, "home", "555 Main St"));
    }

    @Test
    public void addDeliveryAddress_withDuplicatedNickName() throws Exception {
        String restaurantId = randomUUID().toString();
        String addressId = randomUUID().toString();

        fixture
            .given(
                new RestaurantSignedUpEvent(restaurantId, "jdoe@gmail.com"),
                new AddressAddedEvent(restaurantId, addressId, "home", "555 Main St"))
            .when(new AddAddressCommand(restaurantId, addressId, "home", "555 Main St"))
            .expectException(IllegalStateException.class);
    }
}