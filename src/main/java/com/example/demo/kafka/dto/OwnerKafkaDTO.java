package com.example.demo.kafka.dto;

import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.OrderStatusEnum;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@ToString(of = {"orderDTO"})
public class OwnerKafkaDTO implements Serializable {

    private OrderDTO.Response orderDTO;

    @Builder
    public OwnerKafkaDTO(OrderDTO.Response orderDTO) {
        this.orderDTO = orderDTO;
    }
}
