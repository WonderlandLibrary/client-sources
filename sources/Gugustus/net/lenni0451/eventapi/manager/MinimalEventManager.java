package net.lenni0451.eventapi.manager;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.lenni0451.eventapi.events.IEvent;
import net.lenni0451.eventapi.listener.IEventListener;

public class MinimalEventManager {
   private static final Map<Class<? extends IEvent>, List<IEventListener>> EVENT_LISTENER = new ConcurrentHashMap<>();

   public static void call(IEvent event) {
      if (EVENT_LISTENER.containsKey(event.getClass())) {
         EVENT_LISTENER.get(event.getClass()).forEach(l -> {
            try {
               l.onEvent(event);
            } catch (Throwable var3) {
               var3.printStackTrace();
            }
         });
      }
   }

   public static <T extends IEventListener> void register(Class<? extends IEvent> eventType, T listener) {
      EVENT_LISTENER.computeIfAbsent(eventType, c -> new CopyOnWriteArrayList()).add(listener);
   }

   public static <T extends IEventListener> void unregister(T listener) {
      EVENT_LISTENER.entrySet().forEach(entry -> entry.getValue().removeIf(l -> l.equals(listener)));
   }
}
