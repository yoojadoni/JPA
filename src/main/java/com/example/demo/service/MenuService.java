package com.example.demo.service;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.MenuDTO;
import com.example.demo.dto.order.OrderDTO;
import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.Shop;
import com.example.demo.repository.MenuRepository;
import com.example.demo.service.shop.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ShopService shopService;
    @Autowired
    ModelMapper modelMapper;

    //메뉴 전체 조회
    public List<Menu> findAll(){
        return menuRepository.findAll();
    }

    public Page<MenuDTO> findAllWithPagealbeToDTO(Pageable pageable) throws Exception{
        Page<MenuDTO> menuDTOPage = null;
        try {
            menuDTOPage = menuRepository.findAll(pageable).map(MenuDTO::toDto);
            if(menuDTOPage.isEmpty() && menuDTOPage == null){
                throw new CustomException(StatusCodeEnum.NO_DATA);
            }
        } catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        } catch (Exception e){
            throw new Exception();
        }
        return menuDTOPage;
    }

    //MENUID로 조회(PK)
    public Optional<Menu> findById(Long menuId) {
        return menuRepository.findById(menuId);
    }

    public Menu findByMenuId(Long menuId){
        return menuRepository.findById(menuId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Menu> findAllMenuByShopId(Long shopId) throws Exception{
        List<Menu> menuList = new ArrayList<>();
        try {
            Shop shop = shopService.findShopById(shopId).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            menuList = menuRepository.findAllByShop(shop);
        } catch (CustomException e){
            log.debug("해당 메뉴와 가게가 일치 하지 않습니다. SHOP ID : {}", shopId);
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            throw new Exception();
        }
        return menuList;
    }

    public List<Menu> findByMenuIdInAndShopFromDTO(OrderDTO.Request orderDTO) throws Exception{
        List<Menu> menuList = new ArrayList<>();
        try {
            List<Long> id = new ArrayList<>();
            for (int i = 0; i < orderDTO.getOrdersDetails().size(); i++) {
                id.add(orderDTO.getOrdersDetails().get(i).getMenuId());
            }
            Shop shop = Shop.builder()
                    .id(orderDTO.shopId)
                    .build();
            menuList = menuRepository.findByIdInAndShop(id, shop).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            if(menuList.size() != orderDTO.getOrdersDetails().size())
                throw new CustomException(StatusCodeEnum.MENU_NOT_MATCH);

        }catch(CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch(Exception e){
            throw new Exception();
        }
        return menuList;
    }

    public MenuDTO findByMenuIdToDTO(Long menuId) throws Exception {
        MenuDTO menuDTO = new MenuDTO();
        try{
            menuDTO = menuRepository.findById(menuId).map(MenuDTO::toDto).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
        } catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        } catch (Exception e){
            throw new Exception();
        }
        return menuDTO;
    }

    public Menu save(Menu menu){
        return menuRepository.save(menu);
    }

    public MenuDTO saveFromDto(MenuDTO menuDTO) throws Exception{
        Menu menu = Menu.createMenuFromDto(menuDTO);
        try {
            menu = menuRepository.save(menu);
        } catch (Exception e){
            throw new Exception();
        }
        return modelMapper.map(menu, MenuDTO.class);
    }

    @Transactional
    public MenuDTO updateMenuFromDto(MenuDTO menuDTO) throws Exception{
        try{
            Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            menu = Menu.createMenuFromDto(menuDTO);
            menu.setMenuName(menuDTO.getMenuName());
            menu.setPrice(menuDTO.getPrice());
            menu.setUseyn(menuDTO.getUseYn());
            menu.setImageurl(menuDTO.getImageUrl());
        } catch(CustomException e){
            throw new CustomException(e.getStatusCodeEnum(), "메뉴 ID :"+menuDTO.getId()+",가 미존재");
        } catch (Exception e){
            throw new Exception();
        }
        return menuDTO;
    }

    public void deleteMenuFromDTO(Long id) throws Exception {
        try{
            menuRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException(StatusCodeEnum.NO_DATA);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(StatusCodeEnum.BAD_REQUEST, "메뉴 ID :"+id+"는 FK로 삭제 불가");
        } catch (Exception e){
            throw new Exception();
        }
    }
}
