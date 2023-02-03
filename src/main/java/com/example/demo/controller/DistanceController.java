package com.example.demo.controller;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.distance.DistanceDTO;
import com.example.demo.service.distance.DistanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("distance")
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    /**
     * TODO : 네이버 지도 혹은 카카오맵을 통한 거리계산 API 호출필요 혹은 자체계산방식.
     * 임시로 1000, 1500, 2000, 2500 랜덤 생성
     * @param distanceDTO
     * @return
     */
    @GetMapping("/deliveryPrice")
    public ResponseEntity getDeliveryPrice(@RequestBody DistanceDTO distanceDTO) throws Exception {
        distanceDTO = distanceService.getDeiliveryPrice(distanceDTO);
        return ResponseEntity.status(StatusCodeEnum.CREATED.getCode()).body(distanceDTO);
    }

}
