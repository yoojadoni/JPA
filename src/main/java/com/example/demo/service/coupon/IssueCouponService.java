package com.example.demo.service.coupon;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.coupon.CouponStatusEnum;
import com.example.demo.entity.coupon.DiscountTypeEnum;
import com.example.demo.entity.coupon.IssueCoupon;
import com.example.demo.repository.coupon.IssueCouponQueryRepository;
import com.example.demo.repository.coupon.IssueCouponRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IssueCouponService {
    @Autowired
    IssueCouponRepository issueCouponRepository;

    @Autowired
    IssueCouponQueryRepository issueCouponQueryRepository;

    public IssueCoupon saveIssueCoupon(IssueCoupon issueCoupon) throws Exception{
        IssueCoupon issueCouponResult = IssueCoupon.builder().build();
        try {
            issueCouponResult = issueCouponRepository.save(issueCoupon);
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            throw new Exception(e);
        }
        return issueCouponResult;
    }

    @Transactional
    public void updateIssueCoupon(OrderDTO.Request orderDTO) throws Exception{
        try{

            List<IssueCoupon> issueCouponList = issueCouponQueryRepository.findByIssueCouponByCouponId(orderDTO.getCouponList(), null);
            for (IssueCoupon issueCoupon : issueCouponList) {
                issueCoupon.changeStatus(CouponStatusEnum.USED);
            }
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
