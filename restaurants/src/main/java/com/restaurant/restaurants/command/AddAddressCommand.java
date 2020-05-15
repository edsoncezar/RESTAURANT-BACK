package com.restaurant.restaurants.command;

import static lombok.AccessLevel.PRIVATE;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddAddressCommand {
    @TargetAggregateIdentifier
    String restaurantId;
    String addressId;
    String nickName;
    String location;
}
