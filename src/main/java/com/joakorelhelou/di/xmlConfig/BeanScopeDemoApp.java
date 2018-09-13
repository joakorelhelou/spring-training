package com.joakorelhelou.di.xmlConfig;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.lang.String.format;
import static java.lang.System.out;

public class BeanScopeDemoApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanScope-applicationContext.xml");
        CricketCoach theCoach = context.getBean("myCricketCoach", CricketCoach.class);
        out.println(format("Coach fortune is: %s", theCoach.getDailyFortune()));
        out.println(format("Coach email is %s and his team is %s", theCoach.getEmail(), theCoach.getTeam()));
        context.close();
    }

}
