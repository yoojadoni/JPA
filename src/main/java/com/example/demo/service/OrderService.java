package com.example.demo.service;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.configure.exception.PayCancelException;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.order.OrderDetailDTO;
import com.example.demo.dto.order.OrderMenuOptionDTO;
import com.example.demo.entity.Member;
import com.example.demo.entity.OrderStatusEnum;
import com.example.demo.entity.Orders;
import com.example.demo.entity.OrdersDetail;
import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.coupon.CouponStatusEnum;
import com.example.demo.entity.coupon.DiscountTypeEnum;
import com.example.demo.entity.coupon.IssueCoupon;
import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.payment.PayStatusEnum;
import com.example.demo.entity.payment.Payment;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.coupon.IssueCouponQueryRepository;
import com.example.demo.repository.coupon.IssueCouponRepository;
import com.example.demo.service.coupon.IssueCouponService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.order.OrderMenuOptionService;
import com.example.demo.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderMenuOptionService orderMenuOptionService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    IssueCouponService issueCouponService;
    @Autowired
    IssueCouponRepository issueCouponRepository;
    @Autowired
    IssueCouponQueryRepository issueCouponQueryRepository;


    public Optional<Orders> findById(Long id){
        return ordersRepository.findById(id);
    }

    public Optional<OrderDTO> findByIdToDTO(Long id){
        return ordersRepository.findById(id).map(OrderDTO::toDto);
    }

    public Orders saveOrder(Orders orders){
        Orders order = ordersRepository.save(orders);
        return order;
    }
    public OrderDTO saveOrderToDTO(OrderDTO orderDTO) throws Exception {
        Orders orders = Orders.createOrdersFromDto(orderDTO);
        try {
            orders = ordersRepository.save(orders);
        }catch (Exception e){
            throw new Exception();
        }
        return modelMapper.map(orders, OrderDTO.class);
    }

    public OrderDTO findOrderByIdToDTO(Long id){
        Optional<OrderDTO> orderDTO = Optional.of(new OrderDTO());
        try {
            orderDTO = Optional.ofNullable(ordersRepository.findById(id).orElseThrow(EntityNotFoundException::new)).map(OrderDTO::toDto);
        } catch (EntityNotFoundException e) {
            throw new CustomException(StatusCodeEnum.NO_DATA);
        } catch (Exception e){
            throw new CustomException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return orderDTO.get();
    }

    public Page<OrderDTO> findOrderWithPagealbeToDTO(Pageable pageable) throws Exception {
        Page<OrderDTO> orderDtoPage = null;
        try {
            orderDtoPage = ordersRepository.findWithPagenation(pageable).map(OrderDTO::toDto);
            if(orderDtoPage.isEmpty())
                throw new CustomException(StatusCodeEnum.NO_DATA);
        } catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        } catch (Exception e){
            throw new CustomException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return orderDtoPage;
    }

    public void deleteById(Long id){
        try{
            ordersRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new CustomException(StatusCodeEnum.NO_DATA);
        } catch (Exception e){
            throw new CustomException(StatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public OrderDTO updateOrderFromDto(OrderDTO orderDto) throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        try {
            Orders orders = ordersRepository.findByIdToDto(orderDto.getId()).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            orders.setStatus(orderDto.getStatus());
            orderDTO = modelMapper.map(orders, OrderDTO.class);
        } catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        } catch (Exception e){
            throw new Exception();
        }
        return orderDTO;
    }

    /**
     * 결제 금액이 맞는지 확인
     * 1. 메뉴당 가격 확인 
     * 2. 쿠폰 차감금액 확인.
     * 3. 배달료 확인
     * 4. 총 차감금액 확인
     * @param orderDTO
     * @param menuList
     * @throws Exception
     */
    @Transactional
    public void validationPrice(com.example.demo.dto.order.OrderDTO.Request orderDTO, List<Menu> menuList) throws Exception{
        try {
            // 결제 금액 확인을 위해 DB의 메뉴당 가격 합산처리.
            Long amount = 0L;
            for (OrderDetailDTO ordersDetailDTO : orderDTO.getOrdersDetails()) {
                for(Menu menus : menuList) {
                    if(menus.getId() == ordersDetailDTO.getMenuId()){
                        amount += menus.getPrice() * ordersDetailDTO.getAmount();
                    }
                }
            }
            // 쿠폰 금액 차감처리.
            if (!orderDTO.getCouponList().isEmpty()){
                // 쿠폰 리스트 조회.
                List<IssueCoupon> issueCouponList = issueCouponQueryRepository.findByIssueCouponByCouponId(orderDTO.getCouponList(), null);
                // 쿠폰이 사용된 상태 혹은 삭제또는 취소인 상태인 경우 오류 처리
                if (issueCouponList.stream().filter(c -> c.getCouponStatus().equals(CouponStatusEnum.NORMAL)).collect(Collectors.toList()).size() != orderDTO.getCouponList().size()) {
                    throw new CustomException(StatusCodeEnum.COUPON_ISSUED);
                }
                //쿠폰 조회 및 할인금액 확인.
                for (IssueCoupon issueCoupon : issueCouponList) {
                    //쿠폰의 할인 타입이 %인 경우
                    if(issueCoupon.getCoupon().getDiscountType().equals(DiscountTypeEnum.PERCENTAGE)){
                        amount -= amount  * issueCoupon.getCoupon().getDiscountPrice() / 100L;
                    } else {
                        amount = amount - issueCoupon.getCoupon().getDiscountPrice();
                    }
                }
            }

            // TODO : 배달료 정합성체크 개발필요.
            amount += orderDTO.getDeliveryCost();
            if(!amount.equals(orderDTO.getPrice())){
                log.debug("총 결제금액 오류 [CLIENT : {}],[SERVER : {}]", orderDTO.getPrice(), amount);
                throw new CustomException(StatusCodeEnum.PRICE_NOT_MATCH);
            }
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            throw new Exception();
        }
    }
    @Transactional(noRollbackFor=PayCancelException.class) //PG사와의 금액이 다르거나 기타의 이유로 주문의 상태나 결제 취소가 필요하므로 PayCancelException은 commit처리한다.
    public Orders saveOrderInfo(String memberEmail, com.example.demo.dto.order.OrderDTO.Request orderDTO) throws Exception {
        Orders orders = Orders.builder().build();
        try{
            // 주문 생성
            orders = createOrders(memberEmail, orderDTO);
            orderDTO.setId(orders.getId());

            //결제 금액 PG사통해 정합성 체크
            if(!paymentService.checkPaymentValidation(orderDTO.getPayment())) {
                // 금액이 오류가 있으면 취소처리
                paymentService.cancelPayment(orderDTO);
                orders.setStatus(OrderStatusEnum.PayCancelByPG);
                throw new PayCancelException(StatusCodeEnum.PAYMENT_NOT_MATCH_FROM_PG);
            }

            //결제 정보 저장
            Payment payment = Payment.builder()
                    .paymentPK(orderDTO.getPayment().getPaymentPK())
                    .orders(orders)
                    .payStatus(PayStatusEnum.COMPLETE)
                    .payPrice(orders.getPrice())
                    .build();
            paymentService.savePaymentInfo(payment);
            // 주문 상태 결제 완료로 업데이트
            orders.setStatus(OrderStatusEnum.PaySuccess);
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            log.error("주문생성중 오류발생 : {}",e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
        return orders;
    }

    @Transactional(propagation = Propagation.NESTED)
    public Orders createOrders(String memberEmail, com.example.demo.dto.order.OrderDTO.Request orderDTO) throws Exception {
        Orders orders;
        try{
            Member member = memberService.findByEmail(memberEmail).get();
            orderDTO.setMember(member);
            orders = ordersRepository.save(orderDTO.toEntity());
            orders.setStatus(OrderStatusEnum.OrderComplete);
            log.debug("order insert finish order id : {} ", orders.getId());
            // 옵션 내역 생성.
            if(!orderDTO.getOrderMenuOptionList().isEmpty()) {
                for (OrderMenuOptionDTO.Request orderMenuOptionDTO : orderDTO.getOrderMenuOptionList()) {
                    Optional<OrdersDetail> matched = orders.getOrdersDetails().stream()
                            .filter(s-> s.getMenu().getId().equals(orderMenuOptionDTO.getMenuOptionId()))
                            .findFirst();
                    orderMenuOptionDTO.setOrderDetailId(matched.get().getId());
                    orderMenuOptionService.save(orderMenuOptionDTO.toEntity());
                }
            }
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            log.error("주문생성중 오류발생 : {}",e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
        return orders;
    }

}
