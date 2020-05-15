package com.restaurant.restaurants.command.api;

import com.restaurant.restaurants.command.SignUpRestaurantCommand;
import com.restaurant.restaurants.query.Restaurant;
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

import java.util.UUID;
import java.util.concurrent.Future;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/restaurants/commands")
@ResponseStatus(HttpStatus.ACCEPTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestaurantsCommandsController implements ResourceProcessor<RepositoryLinksResource> {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    EntityLinks links;

    @PostMapping("/sign-up")
    public Future<?> signUp(@RequestBody RestaurantRequestBody body) {
        String email = body.getEmail();
        String id = generateId(email);

        FutureCallback<SignUpRestaurantCommand, Object> callback = new FutureCallback<>();
        commandGateway.send(new SignUpRestaurantCommand(id, email), callback);

        return callback
            .thenApply(v -> links.linkForSingleResource(Restaurant.class, id).withSelfRel().getHref())
            .toCompletableFuture();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResourceSupport> getCommands() {
        ResourceSupport commands = new ResourceSupport();
        commands.add(linkTo(methodOn(getClass()).signUp(null)).withRel("sign-up"));
        return ResponseEntity.ok(commands);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        return resource;
    }

    private String generateId(String input) {
        String pattern = "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)";
        return UUID.fromString(md5Hex(input).replaceFirst(pattern, "$1-$2-$3-$4-$5")).toString();
    }
}
