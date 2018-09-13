package com.joakorelhelou.di.javaClassConfig;

import com.joakorelhelou.di.xmlComponentScan.Accessory;
import com.joakorelhelou.di.xmlComponentScan.Animal;

import static java.lang.String.format;

public class Parrot implements Animal {

    private Accessory accessory;

    public Parrot(Accessory accessory){
        this.accessory = accessory;
    }

    public String makeSound() {
        return "Parrot yells creeek!";
    }

    public String getAccessory() {
        return format("Accessory is %s", accessory.getAccessoryName());
    }


}
