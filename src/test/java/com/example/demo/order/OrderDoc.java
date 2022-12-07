package com.example.demo.order;

import com.example.demo.DemoApplication;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Orders;
import com.example.demo.entity.OrdersDetail;
import com.example.demo.repository.OrdersDetailRepository;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrderService;
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
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@DisplayName("Order Rest Doc 생성")
public class OrderDoc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

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
                        .withHost("kkstest.com")
                        .withPort(80)
                )
                .build();
    }

    @BeforeEach
    public void setDocData(){
        String menuname = "아메리카노";
        int price = 1500;
        Menu menu = new Menu(menuname, price);
        menuService.save(menu);
        menuname = "라떼";
        price = 2500;
        Menu menu1 = new Menu(menuname, price);
        menuService.save(menu1);

        Orders order = new Orders(4000, 2);
        OrdersDetail ordersDetail = new OrdersDetail(1500, 1, menu);
        OrdersDetail ordersDetail2 = new OrdersDetail(2500, 2, menu);
        order.addOrdersDetails(ordersDetail);
        order.addOrdersDetails(ordersDetail2);
        ordersRepository.save(order);

        Orders order1 = new Orders(4000, 2);
        OrdersDetail ordersDetail3 = new OrdersDetail(1500, 1, menu1);
        OrdersDetail ordersDetail4 = new OrdersDetail(2500, 2, menu1);
        order1.addOrdersDetails(ordersDetail3);
        order1.addOrdersDetails(ordersDetail4);
        ordersRepository.save(order1);

        Orders order2 = new Orders(4000, 2);
        OrdersDetail ordersDetail5 = new OrdersDetail(1500, 1, menu);
        OrdersDetail ordersDetail6 = new OrdersDetail(2500, 2, menu);
        order2.addOrdersDetails(ordersDetail5);
        order2.addOrdersDetails(ordersDetail6);
        ordersRepository.save(order2);

        Orders order3 = new Orders(4000, 1);
        OrdersDetail ordersDetail7 = new OrdersDetail(1500, 1, menu);
        order3.addOrdersDetails(ordersDetail7);
        ordersRepository.save(order3);

        Orders order4 = new Orders(4000, 1);
        OrdersDetail ordersDetail8 = new OrdersDetail(1500, 1, menu);
        order4.addOrdersDetails(ordersDetail8);
        ordersRepository.save(order4);

        Orders order5 = new Orders(4000, 1);
        OrdersDetail ordersDetail9 = new OrdersDetail(1500, 1, menu);
        order5.addOrdersDetails(ordersDetail9);
        ordersRepository.save(order5);

        Orders order6 = new Orders(4000, 1);
        OrdersDetail ordersDetail10 = new OrdersDetail(1500, 1, menu);
        order6.addOrdersDetails(ordersDetail10);
        ordersRepository.save(order6);


        Orders order7 = new Orders(4000, 1);
        OrdersDetail ordersDetail11 = new OrdersDetail(1500, 1, menu);
        order7.addOrdersDetails(ordersDetail11);
        ordersRepository.save(order7);

        Orders order8 = new Orders(4000, 1);
        OrdersDetail ordersDetail12 = new OrdersDetail(1500, 1, menu);
        order8.addOrdersDetails(ordersDetail12);
        ordersRepository.save(order8);

        Orders order9 = new Orders(4000, 1);
        OrdersDetail ordersDetail13 = new OrdersDetail(1500, 1, menu);
        order9.addOrdersDetails(ordersDetail13);
        ordersRepository.save(order9);


        Orders order10 = new Orders(4000, 1);
        OrdersDetail ordersDetail14 = new OrdersDetail(1500, 1, menu);
        order10.addOrdersDetails(ordersDetail14);
        ordersRepository.save(order10);

        Orders order11 = new Orders(4000, 1);
        OrdersDetail ordersDetail15 = new OrdersDetail(1500, 1, menu);
        order11.addOrdersDetails(ordersDetail15);
        ordersRepository.save(order11);

        Orders order12 = new Orders(4000, 1);
        OrdersDetail ordersDetail16 = new OrdersDetail(1500, 1, menu);
        order11.addOrdersDetails(ordersDetail16);
        ordersRepository.save(order11);

        Orders order13 = new Orders(4000, 1);
        OrdersDetail ordersDetail17 = new OrdersDetail(1500, 1, menu);
        order11.addOrdersDetails(ordersDetail17);
        ordersRepository.save(order11);
    }

    @Test
    @DisplayName("주문조회")
    public void 주문조회(){
        try {
            Long id = 1L;
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/order/{id}", id)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("order-get"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , pathParameters(
                                    parameterWithName("id").description("주문번호")
                            )
                            ,responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("주문아이디(KEY)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("총가격")
                                    ,fieldWithPath("data.quantity")
                                            .type(JsonFieldType.NUMBER).description("주문한 메뉴의 총 수량")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("주문상태")
                                    ,fieldWithPath("data.ordersDetails.[].id")
                                            .type(JsonFieldType.NUMBER).description("주문상세ID")
                                    ,fieldWithPath("data.ordersDetails.[].price")
                                            .type(JsonFieldType.NUMBER).description("메뉴당가격")
                                    ,fieldWithPath("data.ordersDetails.[].amount")
                                            .type(JsonFieldType.NUMBER).description("주문수량")
                                    ,fieldWithPath("data.ordersDetails.[].menuId")
                                            .type(JsonFieldType.NUMBER).description("메뉴아이디")
                                    ,fieldWithPath("data.ordersDetails.[].menuName")
                                            .type(JsonFieldType.STRING).description("메뉴명")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("주문수정")
    public void 주문수정(){
        try {
            //given
            Map<String, Object> map = new HashMap<>();
            map.put("id", 1L);
            map.put("status", "InDelivery");

            mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/order")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(map))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("order-patch"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , requestFields(
                                    fieldWithPath("id").description("주문아이디(KEY)")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("status").description("주문상태")
                                            .type(JsonFieldType.STRING)
                            )
                            , responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터(NULL)").optional()
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("주문전체조회")
    public void 주문전체조회(){
        try {
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/order")
                            .param("page", "0")
                            .param("size", "4")
                            .param("sort", "createdDate,asc")
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("order-getAll"
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
                                    , fieldWithPath("data.list.[].id").type(JsonFieldType.NUMBER).description("메뉴아이디(KEY)")
                                    , fieldWithPath("data.list.[].price").type(JsonFieldType.NUMBER).description("총 금액")
                                    , fieldWithPath("data.list.[].status").type(JsonFieldType.STRING).description("주문상태")
                                    , fieldWithPath("data.list.[].quantity").type(JsonFieldType.NUMBER).description("주문한 메뉴의 총 수량")
                                    , fieldWithPath("data.list.[].ordersDetails.[].id").type(JsonFieldType.NUMBER).description("주문 상세ID")
                                    , fieldWithPath("data.list.[].ordersDetails.[].price").type(JsonFieldType.NUMBER).description("메뉴당가격")
                                    , fieldWithPath("data.list.[].ordersDetails.[].amount").type(JsonFieldType.NUMBER).description("주문수량")
                                    , fieldWithPath("data.list.[].ordersDetails.[].menuId").type(JsonFieldType.NUMBER).description("메뉴아이디")
                                    , fieldWithPath("data.list.[].ordersDetails.[].menuName").type(JsonFieldType.STRING).description("메뉴명")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("주문삭제")
    public void 주문삭제(){
        try {
            Long id = 5L;
            mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/order/{id}",id)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("order-delete"
                            ,preprocessRequest(prettyPrint())
                            ,preprocessResponse(prettyPrint())
                            ,pathParameters(
                                    parameterWithName("id").description("주문번호")
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

    @Test
    @DisplayName("주문생성")
    public void 주문생성(){
        try {
            //given
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setPrice(1500);
            orderDetailDTO.setAmount(2);
            orderDetailDTO.setMenuId(1L);
            orderDetailDTO.setMenuName("아이스아메리카노");

            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
            orderDetailDTOList.add(orderDetailDTO);

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setPrice(3000);
            orderDTO.setQuantity(2);
            orderDTO.setOrdersDetails(orderDetailDTOList);

            mockMvc.perform(RestDocumentationRequestBuilders.post("/api/order")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orderDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("order-create"
                            , preprocessRequest(prettyPrint())
                            , preprocessResponse(prettyPrint())
                            , requestFields(
                                    fieldWithPath("price").description("총 가격")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("quantity").description("주문한 메뉴의 총 수량")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("ordersDetails.[].price").description("주문 가격")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("ordersDetails.[].amount").description("주문 수량")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("ordersDetails.[].menuId").description("메뉴 아이디(MENU KEY)")
                                            .type(JsonFieldType.NUMBER)
                                    , fieldWithPath("ordersDetails.[].menuName").description("메뉴 명")
                                            .type(JsonFieldType.STRING)
                            )
                            , responseFields(
                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지(성공,실패,오류등)")
                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드")
                                    , fieldWithPath("data.id")
                                            .type(JsonFieldType.NUMBER).description("주문아이디(KEY)")
                                    ,fieldWithPath("data.price")
                                            .type(JsonFieldType.NUMBER).description("총 가격")
                                    ,fieldWithPath("data.quantity")
                                            .type(JsonFieldType.NUMBER).description("주문한 메뉴의 총 수량")
                                    ,fieldWithPath("data.status")
                                            .type(JsonFieldType.STRING).description("주문상태")
                                    ,fieldWithPath("data.ordersDetails.[].id")
                                            .type(JsonFieldType.NUMBER).description("주문상세ID")
                                    ,fieldWithPath("data.ordersDetails.[].price")
                                            .type(JsonFieldType.NUMBER).description("메뉴당가격")
                                    ,fieldWithPath("data.ordersDetails.[].amount")
                                            .type(JsonFieldType.NUMBER).description("주문수량")
                                    ,fieldWithPath("data.ordersDetails.[].menuId")
                                            .type(JsonFieldType.NUMBER).description("메뉴아이디")
                            )
                    ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


//    @Test
//    @DisplayName("주문오류")
//    public void 주문오류(){
//        try {
//            Long id = 1L;
//            mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/order/{id}",id)
//                    )
//                    .andDo(print())
//                    .andExpect(status().isOk())
//                    .andDo(document("order-delete"
//                            ,preprocessRequest(prettyPrint())
//                            ,preprocessResponse(prettyPrint())
//                            ,pathParameters(
//                                    parameterWithName("id").description("주문번호")
//                            )
//                            ,responseFields(
//                                    fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메세지")
//                                    , fieldWithPath("status").type(JsonFieldType.NUMBER).description("<<상태 코드, 오류코드표>>")
//                                    , fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터").optional()
//                            )
//                    ));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
