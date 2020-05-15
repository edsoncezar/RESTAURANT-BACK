package com.restaurant.restaurants.query.listeners;

import com.restaurant.restaurants.events.AddressAddedEvent;
import com.restaurant.restaurants.query.Address;
import com.restaurant.restaurants.query.AddressRepository;
import com.restaurant.restaurants.query.Restaurant;
import com.restaurant.restaurants.query.RestaurantRepository;
import com.restaurant.restaurants.query.listeners.AddressAddedEventListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressAddedEventListenerTest {

    @Mock
    RestaurantRepository restaurants;
    @Mock
    AddressRepository addresses;

    AddressAddedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new AddressAddedEventListener(restaurants, addresses);
    }

    @Test
    public void addsANewAddressForExistingRestaurant() throws Exception {
        String restaurantId = randomUUID().toString();
        String addressId = randomUUID().toString();

        Restaurant restaurant = new Restaurant(restaurantId, "jdoe@gmail.com");
        when(restaurants.findOne(restaurantId)).thenReturn(restaurant);

        listener.on(new AddressAddedEvent(restaurantId, addressId, "Home", "555 Main St"));
        verify(addresses).save(refEq(new Address(addressId, "Home", "555 Main St", restaurant)));
    }

    @Test
    public void doesNothingWhenRestaurantIsNotFound() throws Exception {
        String restaurantId = randomUUID().toString();
        String addressId = randomUUID().toString();

        when(restaurants.findOne(restaurantId)).thenReturn(null);

        listener.on(new AddressAddedEvent(restaurantId, addressId, "Home", "555 Main St"));
        verify(addresses, never()).save(any(Address.class));
    }
}