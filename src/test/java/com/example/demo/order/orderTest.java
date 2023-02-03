package com.example.demo.order;

import com.example.demo.DemoApplication;
import com.example.demo.dto.MenuDTO;
import com.example.demo.dto.MenuOptionDTO;
import com.example.demo.dto.order.OrderDTO;
import com.example.demo.dto.order.OrderDetailDTO;
import com.example.demo.dto.order.OrderMenuOptionDTO;
import com.example.demo.entity.*;
import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.coupon.CouponStatusEnum;
import com.example.demo.entity.coupon.DiscountTypeEnum;
import com.example.demo.entity.coupon.IssueCoupon;
import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.menu.MenuOption;
import com.example.demo.entity.owner.Owner;
import com.example.demo.entity.payment.Payment;
import com.example.demo.entity.payment.PaymentPK;
import com.example.demo.entity.payment.PgType;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.OrdersDetailRepository;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.coupon.CouponRepository;
import com.example.demo.repository.coupon.IssueCouponQueryRepository;
import com.example.demo.repository.coupon.IssueCouponRepository;
import com.example.demo.repository.menuOption.MenuOptionRepository;
import com.example.demo.service.MenuService;
import com.example.demo.service.coupon.CouponService;
import com.example.demo.service.coupon.IssueCouponService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.menuOption.MenuOptionService;
import com.example.demo.service.owner.OwnerService;
import com.example.demo.service.shop.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.config.BeanIds;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@DisplayName("Order API 테스트")
public class orderTest {

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
    private ShopService shopService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private IssueCouponRepository issueCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private IssueCouponService issueCouponService;

    @Autowired
    private IssueCouponQueryRepository issueCouponQueryRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MenuOptionService menuOptionService;

    @Autowired
    private MenuOptionRepository menuOptionRepository;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    public void setUP(RestDocumentationContextProvider restDocumentationContextProvider) throws ServletException {
        DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
        delegateProxyFilter.init(
                new MockFilterConfig(context.getServletContext(), BeanIds.SPRING_SECURITY_FILTER_CHAIN));

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
                .addFilter(delegateProxyFilter)
                .build();
    }

    @BeforeEach
    public void setDocData(){

        try {

            Owner owner = Owner.builder()
                    .id("owner_1")
                    .password("asf")
                    .tel("010123123")
                    .name("돈까스 사장")
                    .build();

            Owner ownerResult = ownerService.saveOwner(owner);

            Shop shop = Shop.builder()
                    .name("돈까스")
                    .minOrderPrice(10000)
                    .bizNumber("1231231123")
                    .owner(ownerResult)
                    .addressCode("121-121")
                    .addressDetail("서울시 어디어디어디ㅓ디")
                    .build();

            Shop shopResult = null;
            shopResult = shopService.saveShop(shop);

            String menuname = "아메리카노";
            int price = 1500;
            Menu menu = Menu.builder()
                    .menuName(menuname)
                    .price(price)
                    .shopId(shopResult)
                    .build();

            menuService.save(menu);
            menuname = "라떼";
            price = 2500;
            Menu menu1 = Menu.builder()
                    .menuName(menuname)
                    .price(price)
                    .shopId(shopResult)
                    .build();
            menu1 = menuService.save(menu1);

            MenuOptionDTO.Request menuOptionDTO = MenuOptionDTO.Request
                    .builder()
                    .menu(menu1)
                    .price(500L)
                    .name("ice")
                    .build();
            menuOptionService.saveMenuOptionFromDtoToResponse(menuOptionDTO);


//            Orders order = new Orders(4000L, 2);

            OrdersDetail ordersDetail = OrdersDetail.builder()
                    .price(1500L)
                    .amount(1)
                    .menu(menu)
                    .build();

            OrdersDetail ordersDetail2 = OrdersDetail.builder()
                    .price(2500L)
                    .amount(2)
                    .menu(menu)
                    .build();
            List<OrdersDetail> testList = new ArrayList<>();

            testList.add(ordersDetail);
            testList.add(ordersDetail2);

            Orders order = Orders.builder()
                    .price(4000L)
                    .quantity(2)
                    .ordersDetails(testList)
                    .build();

            order.addOrdersDetails(ordersDetail);
            order.addOrdersDetails(ordersDetail2);
            order = ordersRepository.save(order);

            Orders order1 = new Orders(4000L, 2);
            OrdersDetail ordersDetail3 = new OrdersDetail(1500L, 1, menu1);
            OrdersDetail ordersDetail4 = new OrdersDetail(2500L, 2, menu1);
            order1.addOrdersDetails(ordersDetail3);
            order1.addOrdersDetails(ordersDetail4);
            ordersRepository.save(order1);

            Orders order2 = new Orders(4000L, 2);
            OrdersDetail ordersDetail5 = new OrdersDetail(1500L, 1, menu);
            OrdersDetail ordersDetail6 = new OrdersDetail(2500L, 2, menu);
            order2.addOrdersDetails(ordersDetail5);
            order2.addOrdersDetails(ordersDetail6);
            ordersRepository.save(order2);

            Orders order3 = new Orders(4000L, 1);
            OrdersDetail ordersDetail7 = new OrdersDetail(1500L, 1, menu);
            order3.addOrdersDetails(ordersDetail7);
            ordersRepository.save(order3);

            Orders order4 = new Orders(4000L, 1);
            OrdersDetail ordersDetail8 = new OrdersDetail(1500L, 1, menu);
            order4.addOrdersDetails(ordersDetail8);
            ordersRepository.save(order4);

            Orders order5 = new Orders(4000L, 1);
            OrdersDetail ordersDetail9 = new OrdersDetail(1500L, 1, menu);
            order5.addOrdersDetails(ordersDetail9);
            ordersRepository.save(order5);

            Orders order6 = new Orders(4000L, 1);
            OrdersDetail ordersDetail10 = new OrdersDetail(1500L, 1, menu);
            order6.addOrdersDetails(ordersDetail10);
            ordersRepository.save(order6);


            Orders order7 = new Orders(4000L, 1);
            OrdersDetail ordersDetail11 = new OrdersDetail(1500L, 1, menu);
            order7.addOrdersDetails(ordersDetail11);
            ordersRepository.save(order7);

            Orders order8 = new Orders(4000L, 1);
            OrdersDetail ordersDetail12 = new OrdersDetail(1500L, 1, menu);
            order8.addOrdersDetails(ordersDetail12);
            ordersRepository.save(order8);

            Orders order9 = new Orders(4000L, 1);
            OrdersDetail ordersDetail13 = new OrdersDetail(1500L, 1, menu);
            order9.addOrdersDetails(ordersDetail13);
            ordersRepository.save(order9);


            Orders order10 = new Orders(4000L, 1);
            OrdersDetail ordersDetail14 = new OrdersDetail(1500L, 1, menu);
            order10.addOrdersDetails(ordersDetail14);
            ordersRepository.save(order10);

            Orders order11 = new Orders(4000L, 1);
            OrdersDetail ordersDetail15 = new OrdersDetail(1500L, 1, menu);
            order11.addOrdersDetails(ordersDetail15);
            ordersRepository.save(order11);

            Orders order12 = new Orders(4000L, 1);
            OrdersDetail ordersDetail16 = new OrdersDetail(1500L, 1, menu);
            order11.addOrdersDetails(ordersDetail16);
            ordersRepository.save(order11);

            Orders order13 = new Orders(4000L, 1);
            OrdersDetail ordersDetail17 = new OrdersDetail(1500L, 1, menu);
            order11.addOrdersDetails(ordersDetail17);
            ordersRepository.save(order11);

            Coupon coupon = Coupon.builder()
                    .id("쿠폰번호1")
                    .discountPrice(1000)
                    .name("쿠폰 테스트")
                    .discountType(DiscountTypeEnum.WON)
                    .build();

            couponRepository.save(coupon);

            couponRepository.save(Coupon.builder()
                    .id("쿠폰번호2")
                    .discountPrice(10)
                    .name("쿠폰 테스트")
                    .discountType(DiscountTypeEnum.PERCENTAGE)
                    .build());
            couponRepository.save(Coupon.builder()
                    .id("쿠폰번호3")
                    .discountPrice(10)
                    .name("쿠폰 테스트")
                    .discountType(DiscountTypeEnum.PERCENTAGE)
                    .build()
            );

            IssueCoupon issueCoupon = IssueCoupon.builder()
                    .coupon(coupon)
                    .build();

            issueCouponRepository.save(issueCoupon);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void 쿠폰filter(){
        try{
            Coupon coupon = Coupon.builder()
                    .id("쿠폰번호1")
                    .build();

            List<Coupon> couponList = new ArrayList<>();
            couponList.add(coupon);

            List<IssueCoupon> issueCouponList = issueCouponQueryRepository.findByIssueCouponByCouponId(couponList, null);
            for (IssueCoupon issueCoupon : issueCouponList) {
                System.out.println(issueCoupon.getCouponStatus());
            }
            List<IssueCoupon> test = issueCouponList.stream().filter(c -> c.getCouponStatus().equals(CouponStatusEnum.NORMAL)).collect(Collectors.toList());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void 쿠폰테스트(){
        try{


            Coupon coupon = Coupon.builder()
                    .id("쿠폰번호1")
                    .discountPrice(1000)
                    .name("쿠폰 테스트")
                    .discountType(DiscountTypeEnum.WON)
                    .build();

            couponRepository.save(coupon);

            couponRepository.save(Coupon.builder()
                    .id("쿠폰번호2")
                    .discountPrice(10)
                    .name("쿠폰 테스트")
                    .discountType(DiscountTypeEnum.PERCENTAGE)
                    .build());
            couponRepository.save(Coupon.builder()
                    .id("쿠폰번호3")
                    .discountPrice(10)
                    .name("쿠폰 테스트")
                    .discountType(DiscountTypeEnum.PERCENTAGE)
                    .build()
            );

            Member member = memberService.findById(1L).get();
            /*
           IssueCoupon issueCoupon = IssueCoupon.builder()
                    .coupon(coupon)
                    .member(member)
                    .couponStatus(CouponStatusEnum.NORMAL)
                    .build();

            issueCouponRepository.save(IssueCoupon.builder()
                    .coupon(coupon)
                    .member(member)
                    .couponStatus(CouponStatusEnum.NORMAL)
                    .build());

            issueCouponRepository.save(IssueCoupon.builder()
                    .coupon(Coupon.builder()
                            .id(2L)
                            .build())
                    .member(member)
                    .couponStatus(CouponStatusEnum.NORMAL)
                    .build());

            issueCouponRepository.save(IssueCoupon.builder()
                    .coupon(Coupon.builder()
                            .id(3L)
                            .build())
                    .member(member)
                    .couponStatus(CouponStatusEnum.NORMAL)
                    .build());*/

            List<Coupon> couponList = new ArrayList<Coupon>();
            couponList.add(Coupon.builder().id("쿠폰번호1").build());
            couponList.add(Coupon.builder().id("쿠폰번호2").build());
//            couponList.add(Coupon.builder().id(3L).build());

//            issueCouponService.CouponUsedCheck(couponList);

            List<IssueCoupon> issueCouponList = issueCouponQueryRepository.findByIssueCouponByCouponId(couponList, null);
            for (IssueCoupon issueCoupon : issueCouponList) {
                System.out.println("issueCoupon = " + issueCoupon.getId()+", name-"+issueCoupon.getCoupon());
            }
//            Long test = issueCouponService.discountPrice(couponList, 10000L);
//            System.out.println(".>> test ="+test);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void 메뉴테스트(){
        try{
            Shop shop = Shop.builder()
                    .id(1L)
                    .build();
            List<Long> menuIdList = new ArrayList<>();
            for(int i=0; i<3; i++){
                menuIdList.add(i, i+1L);
            }
            menuRepository.findByIdInAndShop(menuIdList, shop);
            String menuname = "아메리카노";
            int price = 1500;
            Menu menu = Menu.builder()
                    .id(1L)
                    .menuName("아이스아메")
                    .shopId(Shop.builder().id(1L).build())
                    .build();
            MenuOptionDTO.Request menuOptionDTO = MenuOptionDTO.Request.builder()
                    .menu(menu)
                    .price(0L)
                    .name("얼음추가")
                    .build();
            menuOptionService.saveMenuOptionFromDtoToResponse(menuOptionDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("주문생성")
    public void 주문생성테스트(){
        try {
            //given
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setPrice(1500L);
            orderDetailDTO.setAmount(2);
            orderDetailDTO.setMenuId(1L);
            orderDetailDTO.setMenuName("아이스아메리카노");

            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
            orderDetailDTOList.add(orderDetailDTO);

            OrderDetailDTO orderDetailDTO1 = new OrderDetailDTO();
            orderDetailDTO1.setPrice(2500L);
            orderDetailDTO1.setAmount(1);
            orderDetailDTO1.setMenuId(2L);
            orderDetailDTO1.setMenuName("라떼");

            orderDetailDTOList.add(orderDetailDTO1);

            List<Coupon> couponList = new ArrayList<Coupon>();

            couponList.add(Coupon.builder().id("쿠폰번호1").build());
            OrderDTO.Request orderDTO = new OrderDTO.Request();
            orderDTO.setShopId(1L);
            orderDTO.setPrice(5500L);
            orderDTO.setQuantity(3);
            orderDTO.setCouponList(couponList);
            orderDTO.setOrdersDetails(orderDetailDTOList);
            orderDTO.setCouponList(couponList);
            orderDTO.setDeliveryCost(1000L);
            orderDTO.setDeliveryExpcTime("202301060900");

            // 주문한 옵션 정보 생성
            OrderMenuOptionDTO.Request orderMenuOptionDTO = new OrderMenuOptionDTO.Request();
            orderMenuOptionDTO.setMenuOptionId(1L);
            List<OrderMenuOptionDTO.Request> orderMenuOptionDTOList = new ArrayList<>();
            orderMenuOptionDTOList.add(orderMenuOptionDTO);
            orderDTO.setOrderMenuOptionList(orderMenuOptionDTOList);
//            List<MenuOption> menuOptionList = menuOptionRepository.findAll();

            PaymentPK paymentPK = new PaymentPK();
            paymentPK.setPayNumber("123123123");
            paymentPK.setPgType(PgType.NICE);

            Payment payment = Payment.builder()
                    .paymentPK(paymentPK)
                    .build();

            orderDTO.setPayment(payment);

            mockMvc.perform(post("/orders/order")
                                    .header("Authorization" , "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dXJib2tza2ltQGhhbm1haWwubmV0IiwiaXNzIjoia2tzIiwibmFtZSI6Iuq5gOqwleyCsCIsInBpY3R1cmUiOiJodHRwOi8vay5rYWthb2Nkbi5uZXQvZG4vZEVnY2xiL2J0clBSQXhYUjczL295Y2RYZEZRVjFYV0xGb0pxbmM4djAvbTEuanBnIiwiaWF0IjoxNjczOTk3MDQ1LCJleHAiOjE2NzQwMjcwNDV9.luUVhMdn_kMHiee-Rpwq7m5xxA5dnQEyRn1NfWfVWOA")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderDTO))
                    )
                    .andExpect(status().isOk())
                    .andDo(print());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
