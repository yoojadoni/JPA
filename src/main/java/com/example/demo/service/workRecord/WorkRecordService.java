package com.example.demo.service.workRecord;

import com.example.demo.entity.Shop;
import com.example.demo.entity.workRecord.WorkRecord;
import com.example.demo.repository.shop.ShopRepository;
import com.example.demo.repository.workRecord.WorkRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WorkRecordService {

    @Autowired
    WorkRecordRepository workRecordRepository;


    public WorkRecord saveWorkRecord(WorkRecord workRecord){
        return workRecordRepository.save(workRecord);
    }
}
