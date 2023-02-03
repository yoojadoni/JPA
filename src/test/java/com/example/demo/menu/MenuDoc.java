package com.example.demo.menu;

import com.example.demo.DemoApplication;
import com.example.demo.dto.MenuDTO;
import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.Orders;
import com.example.demo.entity.OrdersDetail;
import com.example.demo.repository.OrdersDetailRepository;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@DisplayName("Menu Rest Doc 생성")
public class MenuDoc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    @Autowired
    WebApplicationContext context;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void setUpURI(RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                        .and()
                        .uris()
                        .withHost("localhost")
                        .withPort(8080)
                )
                .build();
    }

    @Test
    @DisplayName("메뉴단일조회")
    public void 메뉴단일조회(){
        try {
            Long id = 1L;
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/menu/{id}", id)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("menu-get"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , pathParameters(
                                    parameterWithName("id").description("메뉴 아이디(KEY)")
                            )
                            ,responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("메뉴 아이디(KEY)")
                                    , fieldWithPath("data.menuName")
                                            .type(JsonFieldType.STRING).description("메뉴명")
                                    , fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("메뉴 가격")
                                    , fieldWithPath("data.option")
                                            .type(JsonFieldType.STRING).description("메뉴 옵션정보").optional()
                                    , fieldWithPath("data.useYn")
                                            .type(JsonFieldType.BOOLEAN).description("사용여부(true/false)")
                                    , fieldWithPath("data.shopId")
                                            .type(JsonFieldType.NUMBER).description("가게 아이디")
                                    , fieldWithPath("data.imageUrl")
                                            .type(JsonFieldType.STRING).description("이미지 URL")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("메뉴전체조회")
    public void 메뉴전체조회(){
        try {
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/menu")
                            .param("page", "0")
                            .param("size", "4")
                            .param("sort", "createdDate,asc")
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("menu-getAll"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , requestParameters(
                                    parameterWithName("page").description("page")
                                    , parameterWithName("size").description("size")
                                    , parameterWithName("sort").description("정렬순서")
                            )
                            ,responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("전체 페이지수")
                                    , fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("전체 데이터수")
                                    , fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("데이터수")
                                    , fieldWithPath("data.list.[].id")
                                            .type(JsonFieldType.NUMBER).description("메뉴 아이디(KEY)")
                                    , fieldWithPath("data.list.[].menuName")
                                            .type(JsonFieldType.STRING).description("메뉴명")
                                    , fieldWithPath("data.list.[].price")
                                            .type(JsonFieldType.NUMBER).description("메뉴 가격")
                                    , fieldWithPath("data.list.[].option")
                                            .type(JsonFieldType.STRING).description("메뉴 옵션정보").optional()
                                    , fieldWithPath("data.list.[].useYn")
                                            .type(JsonFieldType.BOOLEAN).description("사용여부(true/false)")
                                    , fieldWithPath("data.list.[].shopId")
                                            .type(JsonFieldType.NUMBER).description("가게 아이디")
                                    , fieldWithPath("data.list.[].imageUrl")
                                            .type(JsonFieldType.STRING).description("이미지 URL")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @DisplayName("메뉴생성")
    public void 메뉴생성(){
        try {

            //given
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setMenuName("꿀라떼");
            menuDTO.setPrice(4000);
            menuDTO.setUseYn(true);
            menuDTO.setImageUrl("http://localhost:8080/img/asdfasdf.png");

            mockMvc.perform(RestDocumentationRequestBuilders.post("/api/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(menuDTO))
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(document("menu-create"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , requestFields(
                                    fieldWithPath("menuName").description("메뉴명")
                                            .type(JsonFieldType.STRING)
                                    , fieldWithPath("price").description("메뉴 가격")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("useYn").description("사용 여부(true/false)")
                                            .type(JsonFieldType.BOOLEAN).optional()
                                    , fieldWithPath("imageUrl").description("이미지 URL")
                                            .type(JsonFieldType.STRING)
                            )
                            ,responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("메뉴 아이디(KEY)")
                                    , fieldWithPath("data.menuName")
                                            .type(JsonFieldType.STRING).description("메뉴명")
                                    , fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("메뉴 가격")
                                    , fieldWithPath("data.useYn")
                                            .type(JsonFieldType.BOOLEAN).description("사용여부(true/false)")
                                    , fieldWithPath("data.imageUrl")
                                            .type(JsonFieldType.STRING).description("이미지 URL")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @DisplayName("메뉴수정")
    public void 메뉴수정(){
        try {
            //given
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(1L);
            menuDTO.setMenuName("아메리카노");
            menuDTO.setPrice(3000);
            menuDTO.setUseYn(true);
            menuDTO.setImageUrl("http://localhost:8080/img/iced_americano.png");

            mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(menuDTO))
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(document("menu-update"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , requestFields(
                                    fieldWithPath("id").description("메뉴 아이디(KEY)")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("menuName").description("메뉴명")
                                            .type(JsonFieldType.STRING)
                                    , fieldWithPath("price").description("메뉴 가격")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("useYn").description("사용여부(true/false)")
                                            .type(JsonFieldType.BOOLEAN)
                                    , fieldWithPath("imageUrl")
                                            .type(JsonFieldType.STRING).description("이미지 URL")
                            )
                            ,responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("메뉴 아이디(KEY)")
                                    , fieldWithPath("data.menuName")
                                            .type(JsonFieldType.STRING).description("메뉴명")
                                    , fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("메뉴 가격")
                                    , fieldWithPath("data.option")
                                            .type(JsonFieldType.STRING).description("메뉴 옵션정보").optional()
                                    , fieldWithPath("data.useYn")
                                            .type(JsonFieldType.BOOLEAN).description("사용여부(true/false)")
                                    , fieldWithPath("data.imageUrl")
                                            .type(JsonFieldType.STRING).description("이미지 URL")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @DisplayName("메뉴삭제")
    public void 메뉴삭제(){
        try {
            //given
            Long id = 3L;
            mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/menu/{id}", id)
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andDo(document("menu-delete"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , pathParameters(
                                    parameterWithName("id").description("메뉴 아이디(KEY)")
                            )
                            ,responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터").optional()
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
