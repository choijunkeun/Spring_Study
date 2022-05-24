package com.fastcampus.ch3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component("engine")
class Engine {}    // <bean id="engine" class="com.fastcampus.ch3.Engine"/> 과 같음 ("engine")은 생략가능
@Component class SuperEngine extends Engine {}
@Component class TurboEngine extends Engine {}
@Component class Door{}
@Component
class Car{
    @Value("red")
    String color;

    @Value("100")
    int oil;

    // Autowired는 타입으로 빈을 찾아서 주입하는데 같은타입이 여러개면 @Qualifier가 동작해서 이름이 일치하는 빈으로 선택
    @Autowired
    @Qualifier("superEngine")
    Engine engine;      //byType - 타입으로 먼저 검색하고, 여러개만 이름으로 검색 - engine, superEngine, turboEngine

    @Autowired
    Door[] doors;

    public Car() {}
    public Car(String color, int oil, Engine engine, Door[] doors) {
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }
}


public class SpringDiTest {
    public static void main(String[] args) {
        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");

//        Car car = (Car)ac.getBean("car");   //byName, 아래와 같은 문장
        Car car = ac.getBean("car", Car.class);   //byName
//        Car car2 = (Car)ac.getBean(Car.class);   //byType


//        Engine engine = (Engine) ac.getBean("engine");  //byName
////        Engine engine = (Engine) ac.getBean(Engine.class);  //byType--> 에러. 1개의 Engine타입을 예상했는데 engine, superEngine, turboEngine 세개가 찾아져서.
//        Door door = (Door) ac.getBean("door");


        // 싱글톤이기 때문에 car, car2는 같은 해시코드 값이 나온다.
        // 싱글톤 => 클래스의 객체를 하나만 생성하는것.
        // 매번 다른 객체를 생성할 때는 config.xml 의 빈 등록에 scope="prototype" 을 주면 된다. (default는 singleton이다)
//        System.out.println("car = " + car);
//        System.out.println("car2 = " + car2);
//        System.out.println("engine = " + engine);
//        System.out.println("door = " + door);

//        car.setColor("red");
//        car.setOil(100);
//        car.setEngine(engine);
//        car.setDoors(new Door[]{ac.getBean("door", Door.class), (Door) ac.getBean("door")});

        // car.set 말고 config.xml에서 property태그를 이용해 iv를 초기화 할 수 있다.
        // property태그는 setter를 사용하기 때문에 setter가 없으면 xml파일에서 에러 발생함.

        // property태그 대신 constructor-arg태그로 하면 생성자를 이용해 사용할 수 있다.
        System.out.println("car = " + car);
    }
}
