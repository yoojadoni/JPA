package com.example.demo.ownerTest;

import com.example.demo.DemoApplication;
import com.example.demo.entity.owner.Owner;
import com.example.demo.service.owner.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class OwnerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerService ownerService;

   
    @Test
    @DisplayName("주인생성")
    public void 가게주인생성(){
        Owner owner = Owner.builder()
                .id("kks")
                .name("이름")
                .tel("123123")
                .build();
        ownerService.saveOwner(owner);

        Owner result = ownerService.findById("kks").get();
    }


}
