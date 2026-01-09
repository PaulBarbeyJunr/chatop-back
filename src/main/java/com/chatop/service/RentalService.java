package com.chatop.service;

import com.chatop.model.Rental;
import com.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    @Value("${upload.path:uploads}")
    private String uploadPath;

    @Value("${server.base-url:http://localhost:3001}")
    private String baseUrl;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public String savePicture(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), filePath);
            return baseUrl + "/api/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save picture", e);
        }
    }

    public Rental create(String name, Integer surface, Integer price, String picture, String description, Long ownerId) {
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(picture);
        rental.setDescription(description);
        rental.setOwnerId(ownerId);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        return rentalRepository.save(rental);
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Rental findById(Long id) {
        return rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    public Rental update(Long id, String name, Integer surface, Integer price, String description) {
        Rental rental = findById(id);
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setUpdatedAt(LocalDateTime.now());
        return rentalRepository.save(rental);
    }
}
