package com.restaurant.restaurants.query.listeners;

import com.restaurant.restaurants.events.RestaurantSignedUpEvent;
import com.restaurant.restaurants.query.Restaurant;
import com.restaurant.restaurants.query.RestaurantRepository;
import com.restaurant.restaurants.query.listeners.RestaurantSignedUpEventListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantSignedUpEventListenerTest {

    @Mock
    RestaurantRepository restaurants;
    RestaurantSignedUpEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new RestaurantSignedUpEventListener(restaurants);
    }

    @Test
    public void addsANewRestaurant() throws Exception {
        String id = randomUUID().toString();

        listener.on(new RestaurantSignedUpEvent(id, "jdoe@gmail.com"));
        verify(restaurants).save(refEq(new Restaurant(id, "jdoe@gmail.com")));
    }
}