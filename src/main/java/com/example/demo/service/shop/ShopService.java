package com.example.demo.service.shop;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.entity.Shop;
import com.example.demo.entity.owner.Owner;
import com.example.demo.repository.owner.OwnerRepository;
import com.example.demo.repository.shop.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepository;

    public Optional<Shop> findShopById(Long id){
        return shopRepository.findById(id);
    }

    public Shop saveShop(Shop shop) throws Exception{
        return shopRepository.save(shop);
    }

    @Transactional
    public Shop updateShop(Long id, Shop shop) throws Exception{
        try {
            Shop findShop = shopRepository.findById(id).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            findShop.changeShop(shop);
        } catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        } catch (Exception e){
            throw new Exception();
        }
        return shop;
    }
}
