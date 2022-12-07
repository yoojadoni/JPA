package com.example.demo.repository;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Orders;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>
{


    @EntityGraph(attributePaths = {"ordersDetails"})
    List<Orders> findAll();

    @EntityGraph(attributePaths = {"ordersDetails"})
    Optional<Orders> findById(Long id);

    @Query("select o from Orders o JOIN FETCH o.ordersDetails d JOIN FETCH d.menu where o.id = :id")
    Optional<Orders> findByIdToDto(@Param(value = "id") Long id);

    @Query("select distinct o from Orders o JOIN FETCH o.ordersDetails d JOIN FETCH d.menu")
    List<Orders> findAllToDto();

    @BatchSize(size = 100)
    @Query(value =  "select distinct o from Orders o JOIN FETCH o.ordersDetails d JOIN FETCH d.menu"
            ,countQuery ="select count(o) from Orders o"
    )
    Page<Orders> findWithPagenation(Pageable pageable);


    @BatchSize(size = 100)
    @Query(value =  "select distinct o from Orders o JOIN FETCH o.ordersDetails d JOIN FETCH d.menu"
            ,countQuery ="select count(o) from Orders o"
    )
    Page<OrderDTO> findWithPageToDTO(Pageable pageable);
}
