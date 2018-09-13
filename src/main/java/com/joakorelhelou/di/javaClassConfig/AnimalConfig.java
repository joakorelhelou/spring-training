package com.joakorelhelou.di.javaClassConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.joakorelhelou.di.xmlComponentScan")
@PropertySource("classpath:pet.properties")
public class AnimalConfig {

}
