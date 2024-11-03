package net.lenni0451.eventapi.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.lenni0451.eventapi.events.IEvent;
import net.lenni0451.eventapi.events.types.IStoppable;
import net.lenni0451.eventapi.listener.IErrorListener;
import net.lenni0451.eventapi.listener.IEventListener;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.lenni0451.eventapi.reflection.ReflectedEventListener;

public class EventManager {
   private static final Map<Class<? extends IEvent>, List<EventExecutor>> EVENT_LISTENER = new ConcurrentHashMap<>();
   private static final List<IErrorListener> ERROR_LISTENER = new CopyOnWriteArrayList<>();

   public static void call(IEvent event) {
      if (event != null) {
         List<EventExecutor> eventListener = new ArrayList<>();
         if (EVENT_LISTENER.containsKey(event.getClass())) {
            eventListener.addAll(EVENT_LISTENER.get(event.getClass()));
         }

         if (EVENT_LISTENER.containsKey(IEvent.class)) {
            eventListener.addAll(EVENT_LISTENER.get(IEvent.class));
         }

         for(EventExecutor listener : eventListener) {
            try {
               listener.getEventListener().onEvent(event);
            } catch (Throwable var5) {
               if (ERROR_LISTENER.isEmpty()) {
                  throw new RuntimeException(var5);
               }

               ERROR_LISTENER.forEach(errorListener -> errorListener.catchException(var5));
            }

            if (event instanceof IStoppable && ((IStoppable)event).isStopped()) {
               break;
            }
         }
      }
   }

   public static <T extends IEventListener> void register(T listener) {
      register(IEvent.class, (byte)2, listener);
   }

   public static void register(Object listener) {
      Method[] var4;
      for(Method method : var4 = listener.getClass().getMethods()) {
         if (method.isAnnotationPresent(EventTarget.class)) {
            EventTarget anno = method.getAnnotation(EventTarget.class);
            Class[] methodArguments = method.getParameterTypes();
            if (methodArguments.length == 1 && IEvent.class.isAssignableFrom(methodArguments[0])) {
               ReflectedEventListener eventListener = new ReflectedEventListener(listener, methodArguments[0], method);
               if (methodArguments[0].equals(IEvent.class)) {
                  register(anno.priority(), eventListener);
               } else {
                  register(methodArguments[0], anno.priority(), eventListener);
               }
            }
         }
      }
   }

   public static <T extends IEventListener> void register(Class<? extends IEvent> eventType, T listener) {
      register(eventType, (byte)2, listener);
   }

   public static <T extends IEventListener> void register(byte eventPriority, T listener) {
      register(IEvent.class, eventPriority, listener);
   }

   public static <T extends IEventListener> void register(Class<? extends IEvent> eventType, byte eventPriority, T listener) {
      List<EventExecutor> eventListener = EVENT_LISTENER.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList());
      eventListener.add(new EventExecutor(listener, eventPriority));

      for(Entry<Class<? extends IEvent>, List<EventExecutor>> entry : EVENT_LISTENER.entrySet()) {
         List<EventExecutor> eventExecutor = entry.getValue();
         Collections.sort(eventExecutor, new Comparator<EventExecutor>() {
            public int compare(EventExecutor o1, EventExecutor o2) {
               return Integer.compare(o2.getPriority(), o1.getPriority());
            }
         });
      }
   }

   public static void unregister(Object listener) {
      for(Entry<Class<? extends IEvent>, List<EventExecutor>> entry : EVENT_LISTENER.entrySet()) {
         entry.getValue()
            .removeIf(
               eventExecutor -> eventExecutor.getEventListener().equals(listener)
                     || eventExecutor.getEventListener() instanceof ReflectedEventListener
                        && ((ReflectedEventListener)eventExecutor.getEventListener()).getCallInstance().equals(listener)
            );
      }
   }

   public static void addErrorListener(IErrorListener errorListener) {
      if (!ERROR_LISTENER.contains(errorListener)) {
         ERROR_LISTENER.add(errorListener);
      }
   }

   public static boolean removeErrorListener(IErrorListener errorListener) {
      return ERROR_LISTENER.remove(errorListener);
   }
}
