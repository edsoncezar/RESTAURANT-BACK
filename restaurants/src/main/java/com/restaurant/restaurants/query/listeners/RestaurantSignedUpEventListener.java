package com.restaurant.restaurants.query.listeners;

import com.restaurant.restaurants.events.RestaurantSignedUpEvent;
import com.restaurant.restaurants.query.Restaurant;
import com.restaurant.restaurants.query.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestaurantSignedUpEventListener {

    @Autowired
    RestaurantRepository restaurants;

    @EventHandler
    public void on(RestaurantSignedUpEvent event) {
        restaurants.save(new Restaurant(event.getId(), event.getEmail()));
    }
}
