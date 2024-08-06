package com.shroomclient.shroomclientnextgen.events;

import com.shroomclient.shroomclientnextgen.util.C;
import java.lang.reflect.Method;

public class Bus {

    public static void register(Class<?> clazz, Object instance) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubscribeEvent.class)) {
                EventBus.INSTANCE.register(m, instance, clazz);
            }
        }
    }

    public static boolean post(Event event) {
        try {
            EventBus.INSTANCE.post(event);
        } catch (Exception ex) {
            C.logger.error("Failed to post event");
            ex.printStackTrace();
        }
        return event.isCancelled();
    }
}
