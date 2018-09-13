package com.joakorelhelou.di.xmlConfig;

import static java.lang.String.*;
import static java.lang.System.out;

public class TrackCoach implements Coach {

    private FortuneService fortuneService;

    public TrackCoach(FortuneService service) {
        fortuneService = service;
    }

    public String getDailyWorkout() {
        return "Run a hard 5K";
    }

    public String getDailyFortune() {
        return format("Just do it: %s!", fortuneService.getFortune());
    }

    // Add init and destroy method to execute during the bean lifecycle

    //Bean Prototypes do not call destroy methods
    public void doMyCleanupStuff(){
        out.println("Do clean up stuff");
    }

    public void doMyStartupStuff(){
        out.println("Do startup stuff");
    }
}
