package com.restaurant.restaurants.config;

import com.restaurant.restaurants.command.api.RestaurantsCommandsController;
import com.restaurant.restaurants.query.Restaurant;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RestaurantsResourceProcessor implements ResourceProcessor<Resources<Restaurant>> {

    @Override
    public Resources<Restaurant> process(Resources<Restaurant> resources) {
        resources.add(linkTo(methodOn(RestaurantsCommandsController.class).getCommands()).withRel("commands"));
        return resources;
    }
}

