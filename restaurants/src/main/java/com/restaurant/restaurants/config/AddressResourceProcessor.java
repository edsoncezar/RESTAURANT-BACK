package com.restaurant.restaurants.config;

import com.restaurant.restaurants.query.Address;
import com.restaurant.restaurants.query.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class AddressResourceProcessor implements ResourceProcessor<Resource<Address>> {

    @Autowired
    private RepositoryRestMvcConfiguration configuration;

    @Override
    public Resource<Address> process(Resource<Address> resource) {
        Address address = resource.getContent();
        String addressId = address.getId();
        String restaurantId = address.getRestaurant().getId();
        //resource.add(linkTo(methodOn(RestaurantCommandsController.class).getCommands(id)).withRel("commands"));

        String restaurantHref = restaurantHref(restaurantId);
        String addressHref = addressHref(addressId, restaurantHref);
        return resource;
    }

    private String addressHref(String addressId, String restaurantHref) {
        return restaurantHref.concat(format("/addresses/%s", addressId));
    }

    private String restaurantHref(String restaurantId) {
        return configuration.entityLinks()
            .linkToSingleResource(Restaurant.class, restaurantId)
            .withSelfRel().getHref();
    }
}

