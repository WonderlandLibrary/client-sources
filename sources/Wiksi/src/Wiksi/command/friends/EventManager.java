//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.command.friends;

import com.google.common.eventbus.Subscribe;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventManager {
    private static final List<Object> listeners = new ArrayList();

    public EventManager() {
    }

    public static void register(Object listener) {
        listeners.add(listener);
    }

    public static void unregister(Object listener) {
        listeners.remove(listener);
    }

    public static void post(Object event) {
        Iterator var1 = listeners.iterator();

        while(var1.hasNext()) {
            Object listener = var1.next();
            Method[] var3 = listener.getClass().getDeclaredMethods();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Method method = var3[var5];
                if (method.isAnnotationPresent(Subscribe.class) && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                    try {
                        method.invoke(listener, event);
                    } catch (InvocationTargetException | IllegalAccessException var8) {
                        ReflectiveOperationException e = var8;
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
