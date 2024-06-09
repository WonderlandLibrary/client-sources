/*
 * Decompiled with CFR 0_122.
 */
package winter.event;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import winter.event.Event;
import winter.event.EventListener;
import winter.event.EventPriority;
import winter.event.EventPriorityListener;
import winter.event.Listener;
import winter.event.MethodListener;

public class EventSystem {
    private static ArrayList<MethodListener> methods = new ArrayList();

    public static void register(Listener listener) {
        Method[] declaredMethods = listener.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i2 = 0;
        while (i2 < length) {
            Method method = declaredMethods[i2];
            if (EventSystem.isMethodValid(method, listener) && method.getName().equalsIgnoreCase("onEvent")) {
                Class eventClass = method.getParameterTypes()[0];
                MethodListener methodListener = new MethodListener(listener, eventClass, method, EventSystem.getPriority(method));
                methods.add(methodListener);
            }
            ++i2;
        }
        EventSystem.sortListeners();
    }

    public static void register(Object object) {
        Method[] declaredMethods = object.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i2 = 0;
        while (i2 < length) {
            Method method = declaredMethods[i2];
            if (EventSystem.isMethodValid(method, object)) {
                Class eventClass = method.getParameterTypes()[0];
                MethodListener methodListener = new MethodListener(object, eventClass, method, EventSystem.getPriority(method));
                methods.add(methodListener);
            }
            ++i2;
        }
        EventSystem.sortListeners();
    }

    public static boolean isRegistered(Object object) {
        int i2 = 0;
        while (i2 <= methods.size() - 1) {
            MethodListener methodListener = methods.get(i2);
            if (methodListener.getParent().getClass() == object.getClass()) {
                return true;
            }
            ++i2;
        }
        return false;
    }

    public static void unregister(Object object) {
        ArrayList<MethodListener> remove = new ArrayList<MethodListener>();
        for (MethodListener methodListener : methods) {
            if (methodListener.getParent().getClass() != object.getClass()) continue;
            remove.add(methodListener);
        }
        methods.removeAll(remove);
        EventSystem.sortListeners();
    }

    private static void sortListeners() {
        for (MethodListener methodListener : methods) {
            if (methodListener.getPriority() >= 2) continue;
            System.out.println("PRE " + methodListener.getParent().getClass().getSimpleName() + ", " + methodListener.getPriority() + ", " + methodListener.getMethod().getName());
        }
        Collections.sort(methods, new MethodListenerComparator());
        for (MethodListener methodListener : methods) {
            if (methodListener.getPriority() >= 2) continue;
            System.out.println("POST " + methodListener.getParent().getClass().getSimpleName() + ", " + methodListener.getPriority() + ", " + methodListener.getMethod().getName());
        }
    }

    private static int getPriority(Method method) {
        if (!method.isAnnotationPresent(EventPriorityListener.class)) {
            return EventPriority.NORMAL.getPriority();
        }
        EventPriorityListener listener = method.getAnnotation(EventPriorityListener.class);
        return listener.priority().getPriority();
    }

    private static boolean isMethodValid(Method method, Object object) {
        if ((method.isAnnotationPresent(EventListener.class) || method.isAnnotationPresent(EventPriorityListener.class) || object instanceof Listener) && method.getParameterTypes().length == 1) {
            return true;
        }
        return false;
    }

    public static Event call(Event event) {
        for (MethodListener methodListener : methods) {
            if (methodListener.getPriority() >= 2) continue;
            System.out.println("CALL1 " + methodListener.getParent().getClass().getSimpleName() + ", " + methodListener.getPriority() + ", " + methodListener.getMethod().getName());
        }
        int i2 = 0;
        while (i2 <= methods.size() - 1) {
            MethodListener methodListener2 = methods.get(i2);
            if (methodListener2.getPriority() < 2) {
                System.out.println("CALL " + methodListener2.getParent().getClass().getSimpleName() + ", " + methodListener2.getPriority() + ", " + methodListener2.getMethod().getName());
            }
            Class eventClass = event.getClass();
            if (event.isCancelled() || event.hasEnded()) break;
            if (eventClass.getName().equalsIgnoreCase(methodListener2.getEventClass().getName())) {
                EventSystem.invokeMethodListener(methodListener2, event);
            }
            ++i2;
        }
        return event;
    }

    private static void invokeMethodListener(MethodListener methodListener, Event event) {
        block3 : {
            if (methodListener == null) {
                return;
            }
            try {
                methodListener.getMethod().setAccessible(true);
                methodListener.getMethod().invoke(methodListener.getParent(), event);
            }
            catch (Exception e2) {
                if (Minecraft.getMinecraft().thePlayer == null) break block3;
                e2.printStackTrace();
            }
        }
    }

    public static class MethodListenerComparator
    implements Comparator<MethodListener> {
        @Override
        public int compare(MethodListener o1, MethodListener o2) {
            if (o1.getPriority() > o2.getPriority()) {
                return 1;
            }
            if (o1.getPriority() < o2.getPriority()) {
                return -1;
            }
            return 0;
        }
    }

}

