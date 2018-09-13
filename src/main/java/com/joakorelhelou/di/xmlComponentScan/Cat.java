package com.joakorelhelou.di.xmlComponentScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static java.lang.System.out;

@Component("thatSillyAnimal")
public class Cat implements Animal {

    private Accessory accessory;

    @Value("${name}")
    private String name;

    public Cat(){
        out.println(">> Cat initializated");
    }

    public String makeSound() {
        return "Cat yells meow!";
    }

    public String getAccessory() {
        return format("Accessory is %s", accessory.getAccessoryName());
    }

    @Autowired
    @Qualifier(value = "ribbon")
    public void setAccessory(Accessory accessory) {
        out.println(">> Accesory set");
        this.accessory = accessory;
    }

    public String getCatName(){
        return name;
    }

}
