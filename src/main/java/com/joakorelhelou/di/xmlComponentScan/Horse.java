package com.joakorelhelou.di.xmlComponentScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.lang.String.format;
import static java.lang.System.out;

@Component
public class Horse implements Animal {

    @Autowired
    @Qualifier("necktie")
    private Accessory accessory;

    private String name;

    public Horse() {
        name = "No name";
    }

    public String makeSound() {
        return "Horse yells iigghhie!";
    }

    public String getAccessory() {
        return format("Accessory is %s", accessory.getAccessoryName());
    }

    public String getName() {
        return name;
    }

    @PostConstruct
    public void doMyStartupStuff(){
        out.println(">> @PostConstruct Method");
        this.name = "The Horse";
    }

    @PreDestroy
    public void doMyCleanupStuff(){
        out.println(">> @PreDestroy Method");
    }


}
