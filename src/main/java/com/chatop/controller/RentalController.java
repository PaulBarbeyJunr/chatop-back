package com.chatop.controller;

import com.chatop.dto.MessageResponse;
import com.chatop.dto.RentalResponse;
import com.chatop.dto.RentalsResponse;
import com.chatop.model.Rental;
import com.chatop.service.RentalService;
import com.chatop.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;

    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @GetMapping
    public RentalsResponse getAllRentals() {
        var rentals = rentalService.findAll().stream()
            .map(this::toRentalResponse)
            .toList();
        return new RentalsResponse(rentals);
    }

    @GetMapping("/{id}")
    public RentalResponse getRental(@PathVariable Long id) {
        Rental rental = rentalService.findById(id);
        return toRentalResponse(rental);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse createRental(@RequestParam("name") String name,
                                        @RequestParam("surface") Integer surface,
                                        @RequestParam("price") Integer price,
                                        @RequestParam("picture") MultipartFile picture,
                                        @RequestParam("description") String description,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername());
        String pictureUrl = rentalService.savePicture(picture);
        rentalService.create(name, surface, price, pictureUrl, description, user.getId());
        return new MessageResponse("Rental created !");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse updateRental(@PathVariable Long id,
                                        @RequestParam("name") String name,
                                        @RequestParam("surface") Integer surface,
                                        @RequestParam("price") Integer price,
                                        @RequestParam("description") String description) {
        rentalService.update(id, name, surface, price, description);
        return new MessageResponse("Rental updated !");
    }

    private RentalResponse toRentalResponse(Rental rental) {
        return new RentalResponse(
            rental.getId(),
            rental.getName(),
            rental.getSurface(),
            rental.getPrice(),
            rental.getPicture(),
            rental.getDescription(),
            rental.getOwnerId(),
            rental.getCreatedAt(),
            rental.getUpdatedAt()
        );
    }
}
