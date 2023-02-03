package com.example.demo.dto;

import com.example.demo.entity.Orders;
import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.menu.MenuOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MenuOptionDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        public Long id;
        public String name;
        public Long price;
        public Menu menu;
        public String imageUrl;
        //DTO -> entity
        public MenuOption toEntity(){
            MenuOption menuOption = MenuOption.builder()
                    .id(id)
                    .name(name)
                    .menu(menu)
                    .price(price)
                    .imageUrl(imageUrl)
                    .build();
            return menuOption;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        public Long id;
        public String name;
        public Long price;
        public Menu menu;
        public String imageUrl;

        // Entity -> DTO 변환
        public Response(MenuOption menuOption){
            this.id = menuOption.getId();
            this.name = menuOption.getName();
            this.price = menuOption.getPrice();
            this.menu = menuOption.getMenu();
            this.imageUrl = menuOption.getImageUrl();
        }
    }
}
