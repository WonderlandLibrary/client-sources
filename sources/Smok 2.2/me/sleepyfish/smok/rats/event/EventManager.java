package me.sleepyfish.smok.rats.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

// Class from SMok Client by SleepyFish
public class EventManager {

   private final Map<Class<?>, ArrayHelper<EventHelp>> events = new HashMap<>();

   public void register(Object o) {
      for (Method rat : o.getClass().getDeclaredMethods())
         if (!this.smOk(rat))
            this.register(rat, o);
   }

   private void register(Method method, Object o) {
      Class<?> rat = method.getParameterTypes()[0];
      final EventHelp rAt = new EventHelp(o, method, method.getAnnotation(SmokEvent.class).value());

      if (!rAt.meth.isAccessible())
         rAt.meth.setAccessible(true);

      if (this.events.containsKey(rat)) {
         if (!this.events.get(rat).isRat(rAt)) {
            this.events.get(rat).addRat(rAt);
            this.mosk((Class<? extends Event>) rat);
         }
      } else {
         this.events.put(rat, new ArrayHelper<EventHelp>() {
            {
               this.addRat(rAt);
            }
         });
      }
   }

   public void unregister(Object o) {
      for (ArrayHelper<EventHelp> rat : this.events.values()) {
         for (EventHelp rAt : rat)
            if (rAt.obj.equals(o))
               rat.setBigRat(rAt);
      }

      this.msok(true);
   }

   public void msok(boolean b) {
      Iterator<Entry<Class<?>, ArrayHelper<EventHelp>>> rat = this.events.entrySet().iterator();

      while (rat.hasNext()) {
         if (!b || rat.next().getValue().MkOS())
            rat.remove();
      }
   }

   private void mosk(Class<? extends Event> c) {
      ArrayHelper<EventHelp> rat = new ArrayHelper<>();

      for (byte b : EventPriority.KMsok) {
         for (EventHelp rAt : this.events.get(c))
            if (rAt.byta == b)
               rat.addRat(rAt);
      }

      this.events.put(c, rat);
   }

   private boolean smOk(Method method) {
      return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(SmokEvent.class);
   }

   public ArrayHelper<EventHelp> getEvent(Class<? extends Event> clazz) {
      return this.events.get(clazz);
   }

}