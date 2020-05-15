package com.restaurant.restaurants.config;

import com.restaurant.restaurants.command.api.RestaurantsCommandsController;
import com.restaurant.restaurants.query.Restaurant;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RestaurantsPagedResourceProcessor implements ResourceProcessor<PagedResources<Resource<Restaurant>>> {

    @Override
    public PagedResources<Resource<Restaurant>> process(PagedResources<Resource<Restaurant>> resources) {
        resources.add(linkTo(methodOn(RestaurantsCommandsController.class).getCommands()).withRel("commands"));
        return resources;
    }
}

