package com.example.demo.service.payment;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.OrderStatusEnum;
import com.example.demo.entity.Orders;
import com.example.demo.entity.payment.PayStatusEnum;
import com.example.demo.entity.payment.Payment;
import com.example.demo.entity.payment.PaymentPK;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.payment.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrdersRepository ordersRepository;

    /**
     * PG사와 승인번호로 금액이 맞는지 체크한다.
     * TODO: 실제 개발필요
     * @param payment
     * @return
     */
    public boolean checkPaymentValidation(Payment payment){
        switch (payment.getPaymentPK().getPgType()){
            default:
                break;
        }
        return true;
    }

    public Payment savePaymentInfo(Payment payment){
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment findById(Payment payment){
        return paymentRepository.findById(payment.getPaymentPK()).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
    }

    /**
     * TODO: PG사와 승인번호로 정상적인 결제 확인 로직 개발필요
     * @param orderDTO
     * @return
     */
    public Orders cancelPayment(OrderDTO.Request orderDTO){
        // 승인번호
        String payNumber = orderDTO.getPayment().getPaymentPK().getPayNumber();

        // PG사
        String pgType = String.valueOf(orderDTO.getPayment().getPaymentPK().getPgType());

        // TODO: PG사 통해 취소처리 진행
        // PG사로부터 받음금액(변경필요)
        Long pgPrice = orderDTO.getPrice();
        PaymentPK paymentPK = new PaymentPK();
        paymentPK.setPayNumber(payNumber);
        paymentPK.setPgType(orderDTO.getPayment().getPaymentPK().getPgType());
        Payment payment = paymentRepository.findById(paymentPK).get();
        // 결제 취소로 변경
        payment.changeStatus(PayStatusEnum.CANCEL);
        // 주문 상태 취소로 업데이트
        Orders orders = ordersRepository.findById(orderDTO.getId()).get();
        orders.setStatus(OrderStatusEnum.PayCancelByPG);

        return orders;
    }
}
