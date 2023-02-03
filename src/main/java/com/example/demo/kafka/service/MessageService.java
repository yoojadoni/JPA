package com.example.demo.kafka.service;

import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.OrderStatusEnum;
import com.example.demo.entity.Shop;
import com.example.demo.entity.owner.Owner;
import com.example.demo.fcm.dto.NotificationDTO;
import com.example.demo.fcm.service.FCMService;
import com.example.demo.kafka.dto.OwnerKafkaDTO;
import com.example.demo.service.owner.OwnerService;
import com.example.demo.service.shop.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private FCMService fcmService;
    public void sendMessageToOwner(OwnerKafkaDTO ownerKafkaDTO){
       /* try{
            Shop shop = shopService.findShopById(ownerKafkaDTO.getOrderDTO().getShopId()).get();
            log.info(" owner id = {}, fcm = {}", shop.getOwner().getId(), shop.getOwner().getFcm());
            OrderStatusEnum status = ownerKafkaDTO.getOrderDTO().getStatus();
            String title = "";
            String message = "";
            switch (status) {
                case PaySuccess : message = "주문을 확인해주세요. 주문번호 : " +ownerKafkaDTO.getOrderDTO().getId();
                                    title = "주문확인요청.";
                                break;
                case PayCancel  : message = "결제취소 주문을 확인 해주세요. 주문번호 : " +ownerKafkaDTO.getOrderDTO().getId();
                                    title = "주문취소요청";
                                break;
            }

            NotificationDTO notificationDTO = new NotificationDTO(title, message, shop.getOwner().getFcm());

            fcmService.sendAsync(notificationDTO);
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
