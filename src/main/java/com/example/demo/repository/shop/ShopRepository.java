package com.example.demo.repository.shop;

import com.example.demo.entity.Shop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @EntityGraph(attributePaths = {"owner"})
    Optional<Shop> findById(Long id);
}
