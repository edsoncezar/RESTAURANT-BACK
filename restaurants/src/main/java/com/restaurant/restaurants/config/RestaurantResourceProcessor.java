package com.restaurant.restaurants.config;

import com.restaurant.restaurants.command.api.RestaurantCommandsController;
import com.restaurant.restaurants.query.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RestaurantResourceProcessor implements ResourceProcessor<Resource<Restaurant>> {

    @Autowired
    private RepositoryRestMvcConfiguration configuration;

    @Override
    public Resource<Restaurant> process(Resource<Restaurant> resource) {
        String id = resource.getContent().getId();
        resource.add(linkTo(methodOn(RestaurantCommandsController.class).getCommands(id)).withRel("commands"));
        return resource;
    }
}

