package com.joakorelhelou.di.xmlComponentScan;

import org.springframework.stereotype.Component;

@Component
public class Necktie implements Accessory {
    public String getAccessoryName() {
        return "Necktie";
    }
}