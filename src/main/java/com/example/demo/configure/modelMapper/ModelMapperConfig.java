package com.example.demo.configure.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

//    private final ModelMapper modelMapper = new ModelMapper();
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
