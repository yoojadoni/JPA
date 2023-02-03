package com.example.demo.service.menuOption;

import com.example.demo.configure.exception.CustomException;
import com.example.demo.dto.MenuOptionDTO;
import com.example.demo.entity.menu.MenuOption;
import com.example.demo.repository.menuOption.MenuOptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class MenuOptionService {
    @Autowired
    MenuOptionRepository menuOptionRepository;

    @Transactional
    public MenuOptionDTO.Response saveMenuOptionFromDtoToResponse(MenuOptionDTO.Request menuOptionDTO) throws Exception{
        MenuOptionDTO.Response response = new MenuOptionDTO.Response();
        try{
            response = new MenuOptionDTO.Response(
                    menuOptionRepository.save(menuOptionDTO.toEntity())
            );
        }catch (CustomException e){
            throw new CustomException(e.getStatusCodeEnum());
        }catch (Exception e){
            throw new Exception(e);
        }
        return response;
    }
}
