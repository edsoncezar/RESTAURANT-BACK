package com.restaurant.restaurants.command;

import com.restaurant.restaurants.events.RestaurantSignedUpEvent;
import com.restaurant.restaurants.events.AddressAddedEvent;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static java.util.function.Predicate.isEqual;
import static lombok.AccessLevel.PRIVATE;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@FieldDefaults(level = PRIVATE)
public class Restaurant {
    @AggregateIdentifier
    String id;
    String email;
    Map<String, Address> addresses;

    private Restaurant() {
    }

    @CommandHandler
    public Restaurant(SignUpRestaurantCommand command) {
        Assert.isTrue(command.getEmail().contains("@"), () -> "Email must be valid.");
        apply(new RestaurantSignedUpEvent(command.getId(), command.getEmail()));
    }

    @CommandHandler
    public void handle(AddAddressCommand command) {
        boolean duplicated =
            addresses
                .values()
                .stream()
                .map(Address::getNickName)
                .anyMatch(isEqual(command.getNickName()));

        Assert.state(!duplicated, () -> "Address nickname must be unique.");

        apply(new AddressAddedEvent(
            command.getRestaurantId(),
            command.getAddressId(),
            command.getNickName(),
            command.getLocation()));
    }

    @EventSourcingHandler
    public void on(RestaurantSignedUpEvent event) {
        id = event.getId();
        email = event.getEmail();
        addresses = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(AddressAddedEvent event) {
        addresses.put(event.getAddressId(),
            new Address(
                event.getAddressId(),
                event.getNickName(),
                event.getLocation()));
    }
}
