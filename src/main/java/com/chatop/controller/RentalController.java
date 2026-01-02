package com.chatop.controller;

import com.chatop.dto.MessageResponse;
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
}
