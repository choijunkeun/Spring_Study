package com.fastcampus.ch3.diCopy1;

import java.io.FileReader;
import java.util.Properties;

class Car{}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}

public class Main1 {
    public static void main(String[] args) throws Exception {
        Car car = (Car)getObject("car");
        Car car2 = (Car)getObject("car");
        Engine engine = (Engine) getObject("engine");
        System.out.println("car = " + car);
        System.out.println("car2 = " + car2);
        System.out.println("engine = " + engine);
    }

    static Object getObject(String key) throws Exception{
        // 내부적으로 Hashtable을 사용하며 Key, value를 (String, String)로 저장
        // 파일로부터 편리하게 값을 읽고 쓸 수 있는 메서드를 제공함.
        Properties p = new Properties();

        // 지정된 Reader로부터 파일 내용을 목록을 읽어서 key-value로 저장.
        p.load(new FileReader("config.txt"));  //ex) key="car", value="com.fastcampus.ch3.diCopy1.SportsCar"

        // Class 클래스 -> 클래스의 정보를 얻기 위한 클래스, 클래스의 생성자,필드,메서드 정보를 알아낼 수 있다. 한마디로 설계도 클래스이다,
        // Class.forname() -> 클래스 전체이름(패키지가 포함된 이름)을 매개변수 값으로 받고 Class 객체를 리턴한다.
        // getProperty() -> 지정된 key의 value를 얻어 올 수 있다.
        Class clazz = Class.forName(p.getProperty(key));    // 1. p.getProperty("car") = com.fastcampus.ch3.diCopy1.SportsCar
                                                            // 2. Class.forName(com.fastcampus.ch3.diCopy1.SportsCar)

        // clazz에 있는 정보를 이용해 new연산자 없이 객체를 동적으로 생성한다.
        return clazz.newInstance();
    }

    static Car getCar() throws Exception{
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty("car"));

        return (Car)clazz.newInstance();
    }
}
