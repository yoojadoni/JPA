package com.example.demo.service.owner;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.entity.owner.Owner;
import com.example.demo.repository.owner.OwnerRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    public Optional<Owner> findById(String id){
        return ownerRepository.findById(id);
    }

    public Owner saveOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    @Transactional
    public void updateOwnerFCM(Owner owner) throws Exception{
        try{
            Owner result = ownerRepository.findById(owner.getId()).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            result.changeFcm(owner.getFcm());
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            throw  new Exception();
        }
    }
}
