package com.example.demo.service;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Orders;
import com.example.demo.repository.OrdersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ModelMapper modelMapper;

    public Optional<Orders> findById(Long id){
        return ordersRepository.findById(id);
    }

    public Optional<OrderDTO> findByIdToDTO(Long id){
        return ordersRepository.findById(id).map(OrderDTO::toDto);
    }

    public Orders saveOrder(Orders orders){
        Orders order = ordersRepository.save(orders);
        return order;
    }
    public OrderDTO saveOrderToDTO(OrderDTO orderDTO) throws Exception {
        Orders orders = Orders.createOrdersFromDto(orderDTO);
        try {
            orders = ordersRepository.save(orders);
        }catch (Exception e){
            throw new Exception();
        }
        return modelMapper.map(orders, OrderDTO.class);
    }

    public OrderDTO findOrderByIdToDTO(Long id){
        Optional<OrderDTO> orderDTO = Optional.of(new OrderDTO());
        try {
            orderDTO = Optional.ofNullable(ordersRepository.findById(id).orElseThrow(EntityNotFoundException::new)).map(OrderDTO::toDto);
        } catch (EntityNotFoundException e) {
            throw new CustomException(StatusCodeEnum.NO_DATA);
        } catch (Exception e){
            throw new CustomException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return orderDTO.get();
    }

    public Page<OrderDTO> findOrderWithPagealbeToDTO(Pageable pageable) throws Exception {
        Page<OrderDTO> orderDtoPage = null;
        try {
            orderDtoPage = ordersRepository.findWithPagenation(pageable).map(OrderDTO::toDto);
            if(orderDtoPage.isEmpty())
                throw new CustomException(StatusCodeEnum.NO_DATA);
        } catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        } catch (Exception e){
            throw new CustomException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return orderDtoPage;
    }

    public void deleteById(Long id){
        try{
            ordersRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new CustomException(StatusCodeEnum.NO_DATA);
        } catch (Exception e){
            throw new CustomException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public OrderDTO updateOrderFromDto(OrderDTO orderDto) throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        try {
            Orders orders = ordersRepository.findByIdToDto(orderDto.getId()).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            orders.setStatus(orderDto.getStatus());
            orderDTO = modelMapper.map(orders, OrderDTO.class);
        } catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        } catch (Exception e){
            throw new Exception();
        }
        return orderDTO;
    }

}
