package com.joakorelhelou.di.xmlComponentScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@Scope("prototype")
public class Dog implements Animal {

    private Accessory accessory;

    @Autowired
    public Dog(@Qualifier("ribbon") Accessory accessory) {
        this.accessory = accessory;
    }

    public String makeSound() {
        return "Dog yells warf!";
    }

    public String getAccessory() {
        return format("Accessory is %s", accessory.getAccessoryName());
    }
}
