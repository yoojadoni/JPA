package com.example.demo.controller;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.common.util.CommonUtils;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.distance.DistanceDTO;
import com.example.demo.dto.order.OrderDetailDTO;
import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.Orders;
import com.example.demo.entity.menu.Menu;
import com.example.demo.kafka.dto.OwnerKafkaDTO;
import com.example.demo.kafka.service.KafkaProducerService;
import com.example.demo.repository.shop.ShopRepository;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrderService;
import com.example.demo.service.coupon.CouponService;
import com.example.demo.service.coupon.IssueCouponService;
import com.example.demo.service.distance.DistanceService;
import com.example.demo.service.owner.OwnerService;
import com.example.demo.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private IssueCouponService issueCouponService;

    @Autowired
    private CouponService couponService;


    @Autowired
    private OrderService orderService;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    CommonUtils commonUtils = new CommonUtils();
    /**
     * 주문생성
     * @param orderDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/order")
    public ResponseEntity orders(HttpServletRequest req, @RequestBody OrderDTO.Request orderDTO) throws Exception
    {
        String memberEmail = commonUtils.getUserEmail(req);

        // 메뉴가 가게에서 파는게 맞는지 체크.
        List<Menu> menuList = menuService.findByMenuIdInAndShopFromDTO(orderDTO);

        // 결제금액 validation
        orderService.validationPrice(orderDTO, menuList);
        // 주문정보 및 결제정보저장
        Orders orders = orderService.saveOrderInfo(memberEmail, orderDTO);
        // 쿠폰 사용 처리
        issueCouponService.updateIssueCoupon(orderDTO);
        // 리턴 데이터 생성
        OrderDTO.Response response = new OrderDTO.Response(orders);
        // 가게 사장에게 주문 message 전달
        OwnerKafkaDTO ownerKafkaDTO = new OwnerKafkaDTO(response);

//        kafkaProducerService.SendToOwner(ownerKafkaDTO);

        return ResponseEntity.status(StatusCodeEnum.CREATED.getCode()).body(response);
    }

}
