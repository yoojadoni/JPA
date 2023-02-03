package com.example.demo.repository;

import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByUseyn(String useyn);

    @Query("select m from Menu m where m.id = :id")
    Optional<Menu> findById(@Param(value = "id") Long id);

    List<Menu> findAllByShop(Shop shop);

    @Query("select m from Menu m where m.id in (:menuId) and m.shop = :shop")
    List<Menu> findByMenuInIdAndShop(
            @Param(value = "menuId") List<Long> menuId,
            @Param(value = "shop") Shop shop
    );

    Optional<List<Menu>> findByIdInAndShop(List<Long> id, Shop shop);
}
