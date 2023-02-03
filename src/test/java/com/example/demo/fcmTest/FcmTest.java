package com.example.demo.fcmTest;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Member;
import com.example.demo.entity.owner.Owner;
import com.example.demo.fcm.service.FCMService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.owner.OwnerService;
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

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class FcmTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;


    @Autowired
    private OwnerService ownerService;
   
    @Test
    @DisplayName("token 변경")
    public void 토큰변경(){
        try {
            Owner owner = Owner.builder()
                    .id("owner_1")
                    .fcm("eCJUebCTAr4:APA91bFVc3ilFst_BW4tf6rkJC3-4CHMHAETSD1VeWfxDzrO1mB7Z2foqzzrrI2T6zxAAN-JtI1g7Ef-evMbXasyIcY-DizBXQwzeFnZ6ocgOz9ogm9sATSNaUq-3WB3LVYNVMN0V8jW")
                    .build();
            ownerService.updateOwnerFCM(owner);
        }catch (Exception e){
            e.printStackTrace();
        }
//        assertThat(owner.getId()).isEqualTo(result.getId());
//        assertThat(owner.getName()).isEqualTo(result.getName());
//        assertThat(owner.getPassword()).isEqualTo(result.getPassword());
    }


}
