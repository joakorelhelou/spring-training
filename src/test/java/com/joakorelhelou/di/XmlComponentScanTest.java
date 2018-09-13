package com.joakorelhelou.di;

import com.joakorelhelou.di.xmlComponentScan.Animal;
import com.joakorelhelou.di.xmlComponentScan.Cat;
import com.joakorelhelou.di.xmlComponentScan.Dog;
import com.joakorelhelou.di.xmlComponentScan.Horse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class XmlComponentScanTest {

    private static ClassPathXmlApplicationContext context;

    @BeforeClass
    public static void setUp() {
        out.println("Running setUp method");
        context = new ClassPathXmlApplicationContext("annotations-context.xml");
    }

    @Test //Get a Bean annotated with @Component(id)
    public void annotationComponentInjectionIsCorrect() {
        Animal theAnimal = context.getBean("thatSillyAnimal", Animal.class);
        assertThat(theAnimal.makeSound(), is("Cat yells meow!"));
    }

    @Test //Get a Bean annotated with @Component using the default id
    public void annotationComponentWithIdInjectionIsCorrect() {
        Animal theAnimal = context.getBean("dog", Animal.class);
        assertThat(theAnimal.makeSound(), is("Dog yells warf!"));
    }

    //Invalid ids will throw a NoSuchBeanDefinitionException
    @Test(expected = NoSuchBeanDefinitionException.class)
    public void annotationComponentWithInvalidId() {
        context.getBean("invalidID", Animal.class);
    }

    @Test //Verify the dependency is autowired using constructor autowiring
    public void dependencyIsAutowiredInConstructor() {
        Animal theAnimal = context.getBean("dog", Animal.class);
        assertThat(theAnimal.getAccessory(), is("Accessory is Ribbon"));
    }

    @Test //Verify the dependency is autowired using a setter method
    public void dependencyIsAutowiredInSetter() {
        Animal theAnimal = context.getBean("thatSillyAnimal", Animal.class);
        assertThat(theAnimal.getAccessory(), is("Accessory is Ribbon"));
    }

    @Test //Verify the dependency is autowired directly in a field
    public void dependencyIsAutowiredInField() {
        Animal theAnimal = context.getBean("horse", Animal.class);
        assertThat(theAnimal.getAccessory(), is("Accessory is Necktie"));
    }

    @Test //Verify the property value is autowired in a field
    public void propertiesValuesInFileAreAutowired() {
        Cat theAnimal = context.getBean("thatSillyAnimal", Cat.class);
        assertThat((theAnimal).getCatName(), is("Mauricio Macri"));
    }

    @Test //Verify the annotated scope as singleton returns same instance
    public void defaultAnnotatedScopeSingletonIsCorrect() {
        Cat firstCat = context.getBean("thatSillyAnimal", Cat.class);
        Cat secondCat = context.getBean("thatSillyAnimal", Cat.class);

        assertSame(firstCat, secondCat); // same instance
        assertEquals(firstCat.toString(), secondCat.toString()); //same representation
    }

    @Test //Verify the annotated scope as prototype returns different instance
    public void annotatedScopePrototypeIsCorrect() {
        Dog firstDog = context.getBean("dog", Dog.class);
        Dog secondDog = context.getBean("dog", Dog.class);

        assertNotSame(firstDog, secondDog);
        assertNotEquals(firstDog.toString(), secondDog.toString());
    }

    @AfterClass
    public static void tearDown() {
        out.println("Running tearDown method");
        context.close();
    }

    @Test //Verify the dependency is autowired directly in a field
    public void postConstructMethodIsCalled() {
        Horse theAnimal = context.getBean("horse", Horse.class);
        assertThat(theAnimal.getName(), is("The Horse"));
    }

}
