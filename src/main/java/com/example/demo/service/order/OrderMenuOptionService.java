package com.example.demo.service.order;

import com.example.demo.configure.exception.CustomException;
import com.example.demo.entity.order.OrderMenuOption;
import com.example.demo.repository.order.OrderMenuOptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderMenuOptionService {

    @Autowired
    OrderMenuOptionRepository orderMenuOptionRepository;

    public OrderMenuOption save(OrderMenuOption orderMenuOption) throws Exception {
        try{
            orderMenuOption = orderMenuOptionRepository.save(orderMenuOption);
        }catch (CustomException e){

        }catch (Exception e){

        }
        return orderMenuOption;
    }
}
