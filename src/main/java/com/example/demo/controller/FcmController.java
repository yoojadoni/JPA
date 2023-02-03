package com.example.demo.controller;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.owner.Owner;
import com.example.demo.service.owner.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("fcm")
public class FcmController {
    @Autowired
    OwnerService ownerService;

    /**
     * 가게 사장 FCM 토큰 정보 update처리.
     * @param id
     * @param token
     * @return
     * @throws Exception
     */
    @PatchMapping(value = "/owner/{id}/{token}")
    public ResponseEntity createOwnerFcmToken(@PathVariable(value = "id") String id, @PathVariable(value = "token")  String token) throws Exception
    {
        Owner owner = Owner.builder()
                .id(id)
                .fcm(token)
                .build();
        ownerService.updateOwnerFCM(owner);
        return ResponseEntity.status(StatusCodeEnum.UPDATE_SUCCESS.getCode()).build();
    }
}
