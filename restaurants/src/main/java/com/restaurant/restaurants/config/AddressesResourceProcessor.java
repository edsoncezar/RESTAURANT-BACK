package com.restaurant.restaurants.config;

import com.restaurant.restaurants.command.api.AddressesCommandsController;
import com.restaurant.restaurants.query.Address;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AddressesResourceProcessor implements ResourceProcessor<Resources<Address>> {

    @Override
    public Resources<Address> process(Resources<Address> resources) {
        String[] linkParts = resources.getLink("self").getHref().split("/");
        String restaurantId = linkParts[linkParts.length - 2];

        try {
            UUID.fromString(restaurantId);
            resources.add(linkTo(methodOn(AddressesCommandsController.class).getCommands(restaurantId)).withRel("commands"));
        } catch (Exception e) {}

        return resources;
    }
}

