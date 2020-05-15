package com.restaurant.restaurants.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@Wither
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Restaurant {
    @Id
    String id;
    String email;

    @OneToMany(mappedBy = "restaurant")
    List<Address> addresses;

    public Restaurant(String id, String email) {
        this.id = id;
        this.email = email;
        this.addresses = emptyList();
    }

    private Restaurant() {
        this(null, null, null);
    }
}
