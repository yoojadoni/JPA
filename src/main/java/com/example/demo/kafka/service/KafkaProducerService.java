package com.example.demo.kafka.service;

import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.owner.Owner;
import com.example.demo.kafka.dto.OwnerKafkaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducerService {

    @Value("#{system['kafka.topic.owner']}")
    private String OWNERTOPIC;

    @Value("#{system['kafka.topic.member']}")
    private String MEMBERTOPIC;
    @Autowired
    private KafkaTemplate<String, OwnerKafkaDTO> orderKafkaTemplate;

    public void SendToOwner(OwnerKafkaDTO ownerKafkaDTO){
        log.info("To Kafka send message {}", ownerKafkaDTO);
        this.orderKafkaTemplate.send(OWNERTOPIC, ownerKafkaDTO);
    }
}
