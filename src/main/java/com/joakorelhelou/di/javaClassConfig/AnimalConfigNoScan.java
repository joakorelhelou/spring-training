package com.joakorelhelou.di.javaClassConfig;

import com.joakorelhelou.di.xmlComponentScan.Accessory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalConfigNoScan {

    //Bean for Accessory
    @Bean
    public Accessory collarAccessory(){
        return new Collar();
    }

    //Bean for Animal
    @Bean
    public Parrot parrotAnimal(){
        return new Parrot(collarAccessory());
    }

}
