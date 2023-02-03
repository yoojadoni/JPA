package com.example.demo.dto;

import com.example.demo.entity.menu.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class MenuDTO {

    public Long id;
    public String menuName;
    public Long shopId;
    public int price;
    public String imageUrl;
    public Boolean useYn;


    @Builder
    public static MenuDTO toDto(Menu menu){
        return MenuDTO.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .shopId(menu.getShop().getId())
                .price(menu.getPrice())
                .imageUrl(menu.getImageurl())
                .useYn(menu.getUseyn())
                .build();
    }
}
