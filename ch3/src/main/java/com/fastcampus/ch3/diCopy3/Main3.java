package com.fastcampus.ch3.diCopy3;

import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component class Car {}
@Component class SportsCar extends Car {}
@Component class Truck extends Car {}
//@Component
class Engine {}

class AppContext {
    Map map; // 객체 저장소

    // 생성자
    AppContext() {
        map = new HashMap();
        doComponentScan();
    }

    private void doComponentScan() {
        try {
            // 1. 패키지내의 클래스 목록을 가져온다.
            // ClassLoader는 컴파일된 클래스 파일을 JVM 위로 Load하는 동작을 수행한다.
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy3");

            // 2. 반복문으로 클래스를 하나씩 읽어와서 @Component 이 붙어 있는지 확인
            for (ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                // 3. @Component가 붙어있으면 객체를 생성해서 map에 저장.
                if (component != null) {
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) {
        return map.get(key);
    }   // byName

    Object getBean(Class clazz) {   //byType
        for(Object obj : map.values()) {
            if(clazz.isInstance(obj))
                return obj;
        }
        return null;
    }
}

public class Main3 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car");  // byName으로 객체를 검색
        Car car2 = (Car) ac.getBean(Car.class);  // byType으로 객체를 검색
        Engine engine = (Engine) ac.getBean("engine");
        System.out.println("car = " + car);
        System.out.println("car2 = " + car2);
        System.out.println("engine = " + engine);
    }
}
