package com.example.demo.dto;

import com.example.demo.entity.Menu;
import com.example.demo.entity.MenuOptionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class MenuDTO {

    public Long id;
    public String menuName;
    public int price;
    public MenuOptionEnum option;
    public String imageUrl;
    public Boolean useYn;

    @Builder
    public static MenuDTO toDto(Menu menu){
        return MenuDTO.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .option(menu.getOption())
                .imageUrl(menu.getImageurl())
                .useYn(menu.getUseyn())
                .build();
    }
}
