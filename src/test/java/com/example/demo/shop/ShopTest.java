package com.example.demo.shop;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Shop;
import com.example.demo.entity.owner.DeliveryTypeEnum;
import com.example.demo.entity.owner.Owner;
import com.example.demo.entity.workRecord.WorkRecord;
import com.example.demo.repository.shop.ShopRepository;
import com.example.demo.service.owner.OwnerService;
import com.example.demo.service.shop.ShopService;
import com.example.demo.service.workRecord.WorkRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@DisplayName("Shop test")
public class ShopTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private WorkRecordService workRecordService;
   
    @Test
    @DisplayName("shop테스트")
    public void shop테스트(){
        try {
            Owner owner = Owner.builder()
                    .id("kks")
                    .password("passs")
                    .name("이름")
                    .tel("012031023123")
                    .build();
            ownerService.saveOwner(owner);

            Shop shop = Shop.builder()
                    .name("가게이름")
                    .deliveryType(DeliveryTypeEnum.DELIVERY_AND_VISIT)
                    .tel("01023123123")
                    .minOrderPrice(1800)
                    .owner(owner)
                    .build();
            Shop saveResult = shopService.saveShop(shop);

            Shop updateResult = shopService.updateShop(saveResult.getId(), saveResult);

            WorkRecord workRecord = WorkRecord.builder()
                    .shop(saveResult)
                    .build();
            WorkRecord resultWorkRecord = workRecordService.saveWorkRecord(workRecord);
            workRecord.setEndDate();
            resultWorkRecord.setEndDate();
            System.out.println("resultWorkRecord.toString() = " + resultWorkRecord.toString());
            assertThat(resultWorkRecord.getStartDate()).isNotNull();


        }catch (Exception e){
            e.printStackTrace();
        }

//        assertThat(owner.getId()).isEqualTo(result.getId());
//        assertThat(owner.getName()).isEqualTo(result.getName());
//        assertThat(owner.getPassword()).isEqualTo(result.getPassword());
    }


}
