package com.example.demo.kafka.service;

import com.example.demo.kafka.dto.OwnerKafkaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerService {
    @Autowired
    MessageService messageService;

    @KafkaListener(topics= "#{system['kafka.topic.owner']}" , containerFactory = "ownerKafkaDTOConcurrentKafkaListenerContainerFactory")
    public void consume(OwnerKafkaDTO ownerKafkaDTO){
        try {
            messageService.sendMessageToOwner(ownerKafkaDTO);
        }catch (Exception e){
            log.error("kafka Consumer error : {}", e.getMessage());
        }
    }
}
