package com.example.demo.service.distance;

import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.distance.DistanceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@Slf4j
@Service
public class DistanceService {

    /**
     * TODO : 실제 계산 방법으로 변경필요
     * @param distanceDTO
     * @return
     * @throws Exception
     */
    public DistanceDTO getDeiliveryPrice(DistanceDTO distanceDTO) throws Exception {
        try {
            List<Long> payList = new ArrayList<>(asList(1000L, 1500L, 2000L, 2500L));
            Random random = new Random();
            int choice = random.nextInt(3);
            distanceDTO.setDeliveryCost(payList.get(choice));
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            throw new Exception(e);
        }
        return distanceDTO;
    }
}
