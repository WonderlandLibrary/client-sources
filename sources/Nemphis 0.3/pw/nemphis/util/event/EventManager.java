/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.event;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import pw.vertexcode.util.event.ClassData;
import pw.vertexcode.util.event.Event;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.event.exception.InvalidEventException;
import pw.vertexcode.util.event.exception.InvalidEventMethodException;

public class EventManager {
    private static final List<ClassData> CLASS_DATA_LIST = new CopyOnWriteArrayList<ClassData>();

    private EventManager() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void registerClass(Object instanceOfClass) {
        Method[] arrmethod = instanceOfClass.getClass().getMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method refMethod = arrmethod[n2];
            if (!refMethod.isAccessible()) {
                refMethod.setAccessible(true);
            }
            if (refMethod.isAnnotationPresent(EventListener.class)) {
                if (refMethod.getParameterTypes().length == 0) throw new InvalidEventMethodException(refMethod);
                Class eventType = refMethod.getParameterTypes()[0];
                if (!EventManager.isInterfacePresent(eventType.getInterfaces(), Event.class)) throw new InvalidEventException(eventType);
                EventManager.registerMethod(eventType, refMethod, instanceOfClass);
            }
            ++n2;
        }
    }

    public static void unregisterClass(Object classInstance) {
        ArrayList<ClassData> remove = new ArrayList<ClassData>();
        for (ClassData data2 : CLASS_DATA_LIST) {
            if (data2.getClassInstance() != classInstance) continue;
            remove.add(data2);
        }
        if (remove.size() != 0) {
            for (ClassData data2 : remove) {
                CLASS_DATA_LIST.remove(data2);
            }
        }
    }

    public static void unregisterClass(Class<?> rawClass) {
        ArrayList<ClassData> remove = new ArrayList<ClassData>();
        for (ClassData data2 : CLASS_DATA_LIST) {
            if (data2.getClassInstance().getClass() != rawClass) continue;
            remove.add(data2);
        }
        if (remove.size() != 0) {
            for (ClassData data2 : remove) {
                CLASS_DATA_LIST.remove(data2);
            }
        }
    }

    public static void registerMethod(Class<?> event, Method method, Object classInstance) {
        try {
            CLASS_DATA_LIST.add(new ClassData(classInstance, method, (Event)event.newInstance()));
        }
        catch (InstantiationException e) {
            System.out.println("Something went wrong while adding the content to datalist!");
            System.out.println("Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
        catch (IllegalAccessException e) {
            System.out.println("Something went wrong while adding the content to datalist!");
            System.out.println("Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getStackTrace());
        }
    }

    private static boolean isInterfacePresent(Class<?>[] interfaceList, Class<?> searchedInterface) {
        if (searchedInterface.isInterface()) {
            Class<?>[] arrclass = interfaceList;
            int n = arrclass.length;
            int n2 = 0;
            while (n2 < n) {
                Class entry = arrclass[n2];
                if (entry.isInterface() && entry == searchedInterface) {
                    return true;
                }
                ++n2;
            }
        }
        return false;
    }

    public static void fire(Event event) {
        List<ClassData> classcopy = CLASS_DATA_LIST;
        for (ClassData data : classcopy) {
            if (data.getEvent().getClass() != event.getClass()) continue;
            data.fire();
        }
    }
}

