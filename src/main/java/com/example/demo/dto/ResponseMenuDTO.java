package com.example.demo.dto;

import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.menu.MenuOptionEnum;
import lombok.*;

@Data
@Builder
public class ResponseMenuDTO {

    private Long id;
    private String menuname;
    private int price;
    private MenuOptionEnum option;
    private String imageurl;

    @Data
    @NoArgsConstructor
    @ToString(of={"id", "menuname", "price", "option", "imageurl"})
    public static class Request{
        private Long id;
        private String menuname;
        private int price;
        private MenuOptionEnum option;
        private String imageurl;
    }

    @Data
    @ToString(of={"id", "menuname", "price", "option", "imageurl"})
    @NoArgsConstructor
    public static class Response{
        private Long id;
        @NonNull
        private String menuname = "";
        private int price = 0;
        private MenuOptionEnum option;
        private String imageurl = "";
        public void setResponse(Menu menu){
            this.id = menu.getId();
            this.menuname = menu.getMenuName();
            this.price = menu.getPrice();


            if(menu.getImageurl() != null && !menu.getImageurl().equalsIgnoreCase("")) {
                this.imageurl = menu.getImageurl();
            }

        }
    }
}
