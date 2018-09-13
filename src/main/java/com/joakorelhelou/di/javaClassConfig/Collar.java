package com.joakorelhelou.di.javaClassConfig;

import com.joakorelhelou.di.xmlComponentScan.Accessory;
import org.springframework.stereotype.Component;

@Component
public class Collar implements Accessory {
    public String getAccessoryName() {
        return "Collar";
    }
}