package me.zero.alpine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class EventManager implements EventBus {
   private final Map SUBSCRIPTION_CACHE = new HashMap();
   private final Map SUBSCRIPTION_MAP = new HashMap();
   private final List ATTACHED_BUSES = new ArrayList();

   public void subscribe(Object var1) {
      List var2 = (List)this.SUBSCRIPTION_CACHE.computeIfAbsent(var1, EventManager::lambda$subscribe$1);
      var2.forEach(this::subscribe);
      if (!this.ATTACHED_BUSES.isEmpty()) {
         this.ATTACHED_BUSES.forEach(EventManager::lambda$subscribe$2);
      }

   }

   public void subscribe(Object... var1) {
      Arrays.stream(var1).forEach(this::subscribe);
   }

   public void subscribe(Iterable var1) {
      var1.forEach(this::subscribe);
   }

   public void unsubscribe(Object var1) {
      List var2 = (List)this.SUBSCRIPTION_CACHE.get(var1);
      if (var2 != null) {
         this.SUBSCRIPTION_MAP.values().forEach(EventManager::lambda$unsubscribe$3);
         if (!this.ATTACHED_BUSES.isEmpty()) {
            this.ATTACHED_BUSES.forEach(EventManager::lambda$unsubscribe$4);
         }

      }
   }

   public void unsubscribe(Object... var1) {
      Arrays.stream(var1).forEach(this::unsubscribe);
   }

   public void unsubscribe(Iterable var1) {
      var1.forEach(this::unsubscribe);
   }

   public void post(Object var1) {
      List var2 = (List)this.SUBSCRIPTION_MAP.get(var1.getClass());
      if (var2 != null) {
         var2.forEach(EventManager::lambda$post$5);
      }

      if (!this.ATTACHED_BUSES.isEmpty()) {
         this.ATTACHED_BUSES.forEach(EventManager::lambda$post$6);
      }

   }

   public void attach(EventBus var1) {
      if (!this.ATTACHED_BUSES.contains(var1)) {
         this.ATTACHED_BUSES.add(var1);
      }

   }

   public void detach(EventBus var1) {
      if (this.ATTACHED_BUSES.contains(var1)) {
         this.ATTACHED_BUSES.remove(var1);
      }

   }

   private static boolean isValidField(Field var0) {
      return var0.isAnnotationPresent(EventHandler.class) && Listener.class.isAssignableFrom(var0.getType());
   }

   private static Listener asListener(Object var0, Field var1) {
      try {
         boolean var2 = var1.isAccessible();
         var1.setAccessible(true);
         Listener var3 = (Listener)var1.get(var0);
         var1.setAccessible(var2);
         if (var3 == null) {
            return null;
         } else if (var3.getPriority() <= 5 && var3.getPriority() >= 1) {
            return var3;
         } else {
            throw new RuntimeException("Event Priority out of bounds! %s");
         }
      } catch (IllegalAccessException var4) {
         return null;
      }
   }

   private void subscribe(Listener var1) {
      List var2 = (List)this.SUBSCRIPTION_MAP.computeIfAbsent(var1.getTarget(), EventManager::lambda$subscribe$7);

      int var3;
      for(var3 = 0; var3 < var2.size() && var1.getPriority() >= ((Listener)var2.get(var3)).getPriority(); ++var3) {
      }

      var2.add(var3, var1);
   }

   private static List lambda$subscribe$7(Class var0) {
      return new ArrayList();
   }

   private static void lambda$post$6(Object var0, EventBus var1) {
      var1.post(var0);
   }

   private static void lambda$post$5(Object var0, Listener var1) {
      var1.invoke(var0);
   }

   private static void lambda$unsubscribe$4(Object var0, EventBus var1) {
      var1.unsubscribe(var0);
   }

   private static void lambda$unsubscribe$3(List var0, List var1) {
      Objects.requireNonNull(var0);
      var1.removeIf(var0::contains);
   }

   private static void lambda$subscribe$2(Object var0, EventBus var1) {
      var1.subscribe(var0);
   }

   private static List lambda$subscribe$1(Object var0) {
      return (List)Arrays.stream(var0.getClass().getDeclaredFields()).filter(EventManager::isValidField).map(EventManager::lambda$subscribe$0).filter(Objects::nonNull).collect(Collectors.toList());
   }

   private static Listener lambda$subscribe$0(Object var0, Field var1) {
      return asListener(var0, var1);
   }
}
