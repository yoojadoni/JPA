package com.example.demo.service;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.ResponseMenuDTO;
import com.example.demo.dto.MenuDTO;
import com.example.demo.entity.Menu;
import com.example.demo.repository.MenuRepository;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

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
            menu.setMenuName(menuDTO.getMenuName());
            menu.setPrice(menuDTO.getPrice());
            menu.setUseyn(menuDTO.getUseYn());
            menu.setOption(menuDTO.getOption());
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
