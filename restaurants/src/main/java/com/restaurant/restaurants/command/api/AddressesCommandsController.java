package com.restaurant.restaurants.command.api;

import com.restaurant.restaurants.command.AddAddressCommand;
import com.restaurant.restaurants.query.Address;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/restaurants/{restaurantId}/addresses/commands")
@ResponseStatus(HttpStatus.ACCEPTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddressesCommandsController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    EntityLinks links;

    @PostMapping("/add")
    public Future<?> addAddress(@PathVariable String restaurantId, @RequestBody AddressRequestBody body) {
        String addressId = randomUUID().toString();
        String location = body.getLocation();
        String nickName = body.getNickName();

        FutureCallback<AddAddressCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new AddAddressCommand(restaurantId, addressId, nickName, location), callback);

        return callback
            .thenApply(v -> links.linkForSingleResource(Address.class, addressId).withSelfRel().getHref())
            .toCompletableFuture();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResourceSupport> getCommands(@PathVariable String restaurantId) {
        ResourceSupport commands = new ResourceSupport();
        commands.add(linkTo(methodOn(getClass()).addAddress(restaurantId, null)).withRel("add"));
        return ResponseEntity.ok(commands);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        return resource;
    }

}
