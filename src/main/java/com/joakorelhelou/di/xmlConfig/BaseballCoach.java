package com.joakorelhelou.di.xmlConfig;

public class BaseballCoach implements Coach{

    private FortuneService fortuneService;

    public BaseballCoach(FortuneService service){
        fortuneService = service;
    }

    public String getDailyWorkout() {
        return "Spend 30 minutes on batting practice";
    }

    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

}
