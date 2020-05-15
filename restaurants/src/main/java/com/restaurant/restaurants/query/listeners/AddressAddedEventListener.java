package com.restaurant.restaurants.query.listeners;

import com.restaurant.restaurants.events.AddressAddedEvent;
import com.restaurant.restaurants.query.Address;
import com.restaurant.restaurants.query.AddressRepository;
import com.restaurant.restaurants.query.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddressAddedEventListener {

    @Autowired
    RestaurantRepository restaurants;

    @Autowired
    AddressRepository addresses;

    @EventHandler
    public void on(AddressAddedEvent event) {
        Optional
            .ofNullable(restaurants.findOne(event.getRestaurantId()))
            .map(restaurant -> new Address(
                event.getAddressId(),
                event.getNickName(),
                event.getLocation(),
                restaurant))
            .ifPresent(addresses::save);
    }
}
