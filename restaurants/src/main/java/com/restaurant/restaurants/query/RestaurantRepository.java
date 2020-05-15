package com.restaurant.restaurants.query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, String> {

    @RestResource(exported = false)
    Restaurant save(Restaurant restaurant);

    @RestResource(exported = false)
    void delete(Restaurant restaurant);
}
