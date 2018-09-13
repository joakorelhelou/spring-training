package com.joakorelhelou.di;

import com.joakorelhelou.di.xmlConfig.Coach;
import com.joakorelhelou.di.xmlConfig.CricketCoach;
import org.junit.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class XmlContextTest {

    private static ClassPathXmlApplicationContext context;
    private static ClassPathXmlApplicationContext scopeConext;

    @BeforeClass
    public static void setUp() {
        out.println("Running setUp method");
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        scopeConext = new ClassPathXmlApplicationContext("beanScope-applicationContext.xml");
    }

    @Test
    public void xmlIoInjectionIsCorrect() {
        Coach theCoach = context.getBean("myCoach", Coach.class);
        assertThat(theCoach.getDailyWorkout(), is("Run a hard 5K"));
    }

    @Test
    public void xmlDiAndValueSetters() {
        CricketCoach theCoach = context.getBean("myCricketCoach", CricketCoach.class);

        assertThat(theCoach.getDailyFortune(), is("Today is your lucky day"));
        assertThat(theCoach.getEmail(), is("john.doe@foo.com"));
        assertThat(theCoach.getTeam(), is("Chicago Cubs"));
    }

    /**
     * Default bean context is Singleton. Therefore two references will point to the same instance
     */
    @Test
    public void beanScopesSingletons() {
        Coach theCoach = scopeConext.getBean("myCoach", Coach.class);
        Coach alphaCoach = scopeConext.getBean("myCoach", Coach.class);

        assertSame(theCoach, alphaCoach); // same instance
        assertEquals(theCoach.toString(), alphaCoach.toString()); //same representation

    }

    /**
     * In prototype scope, new instances are created each time.
     */
    @Test
    public void beanScopesPrototype() {
        Coach theCoach = scopeConext.getBean("myCoachPrototype", Coach.class);
        Coach alphaCoach = scopeConext.getBean("myCoachPrototype", Coach.class);

        assertNotSame(theCoach, alphaCoach);
        assertNotEquals(theCoach.toString(), alphaCoach.toString());

    }



    @AfterClass
    public static void tearDown() {
        out.println("Running tearDown method");
        context.close();
        scopeConext.close();
    }

}
