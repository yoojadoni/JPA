package com.example.demo.member;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Member;
import com.example.demo.service.member.MemberService;
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
public class MemberTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;


   
    @Test
    @DisplayName("멤버생성")
    public void 멤버생성(){
        try {
            Member member = Member.builder()
                    .name("멤버1")
                    .addressCode("123-123")
                    .addressDetail("서울시 마포구 마포동")
                    .tel("010123123")
                    .email("abced@kakao.com")
                    .provider("kakao")
                    .build();

            Long id = memberService.saveMember(member).getId();

            Member member1 = Member.builder()
                    .id(id)
                    .name("멤버1222222222")
                    .addressCode("123-123")
                    .addressDetail("서울시 마포구 마포동 2222222222")
                    .tel("010123123")
                    .email("abced@kakao.com")
                    .provider("kakao")
                    .build();

            memberService.updateMemberById(member1);
        }catch (Exception e){
            e.printStackTrace();
        }
//        assertThat(owner.getId()).isEqualTo(result.getId());
//        assertThat(owner.getName()).isEqualTo(result.getName());
//        assertThat(owner.getPassword()).isEqualTo(result.getPassword());
    }


}
