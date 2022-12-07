package com.example.demo.controller;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class APIOrderController {

    @Autowired
    OrderService orderService;

    /**
     * 주문 전체 조회
     * @param pageable
     * @return ResponseEntity OrderDTO
     * @throws Exception
     */
    @GetMapping("/order")
    public ResponseEntity getOrderList(Pageable pageable) throws Exception {
        Page<OrderDTO> orderDTOPage = orderService.findOrderWithPagealbeToDTO(pageable);
        return ResponseEntity.ok().body(orderDTOPage);
    }

    /**
     * 주문 단건 조회
     * @param id
     * @return ResponseEntity OrderDTO
     * @throws Exception
     */
    @GetMapping("/order/{id}")
    public ResponseEntity getOrder(@PathVariable Long id) throws Exception {
        OrderDTO orderDTO = orderService.findOrderByIdToDTO(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    /**
     * 주문 삭제
     * @param id
     * @return ResponseEntity
     * @throws Exception
     */
    @DeleteMapping("/order/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) throws Exception {
        orderService.deleteById(id);
        return ResponseEntity.status(StatusCodeEnum.DELETE_SUCCESS.getCode()).build();
    }

    /**
     * 주문 상태 변경
     * @param orderDTO
     * @return ResponseEntity
     * @throws Exception
     */
    @PatchMapping("/order")
    public ResponseEntity updateOrder(@RequestBody OrderDTO orderDTO) throws Exception{
        orderService.updateOrderFromDto(orderDTO);
        return ResponseEntity.status(StatusCodeEnum.UPDATE_SUCCESS.getCode()).build();
    }

    /**
     * 주문생성
     * @param orderDTO
     * @return ResponseEntity orderDTO
     * @throws Exception
     */
    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody OrderDTO orderDTO) throws Exception{
        OrderDTO orderDto = orderService.saveOrderToDTO(orderDTO);
        return ResponseEntity.status(StatusCodeEnum.CREATED.getCode()).body(orderDto);
    }
}
