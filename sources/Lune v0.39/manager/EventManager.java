package me.superskidder.lune.manager;

import me.superskidder.lune.manager.event.Event;
import me.superskidder.lune.manager.event.EventTarget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: QianXia
 * @description: event manager
 * @create: 2021/01/12-19:52
 */
public class EventManager {
    public static Map<Method, Class<?>> registeredMethodMap = new HashMap<>();
    public static Map<Method, Object> methodObjectMap = new HashMap<>();

    public static void register(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        finding:
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation.annotationType() != EventTarget.class) {
                    continue;
                }

                if (method.getParameterTypes().length == 0) {
                    continue;
                }
                registeredMethodMap.put(method, method.getParameterTypes()[0]);
                methodObjectMap.put(method, obj);
                continue finding;
            }
        }
    }

    /**
     * 注销某个类中的所有已注册的方法
     * @param obj 类的对象
     */
    public static void unregister(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            registeredMethodMap.remove(method);
            methodObjectMap.remove(method);
        }
    }

    /**
     * 注销指定的方法
     * @param obj 类的对象
     * @param methodToUnregister 需注册的方法
     */
    public static void unregister(Object obj, Method methodToUnregister) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if(method == methodToUnregister) {
                registeredMethodMap.remove(method);
                methodObjectMap.remove(method);
            }
        }
    }

    public static Event call(Event event) {
        try {
            registeredMethodMap.forEach((method, eventClazz) -> {
                if (event.getClass() != eventClazz) {
                    return;
                }
                Object obj = methodObjectMap.get(method);
                method.setAccessible(true);
                try {
                    method.invoke(obj, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }catch (ConcurrentModificationException e){
            // ssh! Don't tell others the problem here will not affect the operation
        }
        return event;
    }
}
