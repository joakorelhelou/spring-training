package com.joakorelhelou.di.xmlComponentScan;

import org.springframework.stereotype.Component;

@Component
public class Ribbon implements Accessory {
    public String getAccessoryName() {
        return "Ribbon";
    }
}