package com.example.Bakery.Management.System.Repository;

import com.example.Bakery.Management.System.Entity.SourceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceImageRepository extends JpaRepository<SourceImage, Long> {
    Optional<SourceImage> findByName(String name);
}
