package cc.novoline.events;

import cc.novoline.events.EventManager$1;
import cc.novoline.events.EventManager$MethodData;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Event;
import cc.novoline.events.types.Priority;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import net.acE;

public final class EventManager {
   private static final Map REGISTRY_MAP = new HashMap();
   private static String[] b;

   public static void register(Object var0) {
      b();
      Method[] var2 = var0.getClass().getDeclaredMethods();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         Method var5 = var2[var4];
         if(isMethodBad(var5)) {
            ;
         }

         register(var5, var0);
         ++var4;
      }

   }

   public static void register(Object var0, Class var1) {
      b();
      Method[] var3 = var0.getClass().getDeclaredMethods();
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         Method var6 = var3[var5];
         if(isMethodBad(var6, var1)) {
            ;
         }

         register(var6, var0);
         ++var5;
      }

      if(acE.b() == null) {
         b(new String[4]);
      }

   }

   public static void unregister(Object var0) {
      b();
      Iterator var2 = REGISTRY_MAP.values().iterator();
      if(var2.hasNext()) {
         List var3 = (List)var2.next();
         var3.removeIf(EventManager::lambda$unregister$0);
      }

      cleanMap(true);
   }

   public static void unregister(Object var0, Class var1) {
      String[] var2 = b();
      if(REGISTRY_MAP.containsKey(var1)) {
         ((List)REGISTRY_MAP.get(var1)).removeIf(EventManager::lambda$unregister$1);
         cleanMap(true);
      }

   }

   private static void register(Method var0, Object var1) {
      b();
      Class var3 = var0.getParameterTypes()[0];
      EventManager$MethodData var4 = new EventManager$MethodData(var1, var0, ((EventTarget)var0.getAnnotation(EventTarget.class)).value());
      if(!var4.getTarget().isAccessible()) {
         var4.getTarget().setAccessible(true);
      }

      if(REGISTRY_MAP.containsKey(var3)) {
         if(((List)REGISTRY_MAP.get(var3)).contains(var4)) {
            return;
         }

         ((List)REGISTRY_MAP.get(var3)).add(var4);
         sortListValue(var3);
      }

      REGISTRY_MAP.put(var3, new EventManager$1(var4));
   }

   public static void removeEntry(Class var0) {
      b();
      Iterator var2 = REGISTRY_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         if(((Class)((Entry)var2.next()).getKey()).equals(var0)) {
            var2.remove();
            break;
         }
      }

   }

   public static void cleanMap(boolean var0) {
      b();
      Iterator var2 = REGISTRY_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         if(!var0 || ((List)((Entry)var2.next()).getValue()).isEmpty()) {
            var2.remove();
            break;
         }
      }

   }

   private static void sortListValue(Class var0) {
      b();
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      byte[] var3 = Priority.VALUE_ARRAY;
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         byte var6 = var3[var5];
         Iterator var7 = ((List)REGISTRY_MAP.get(var0)).iterator();
         if(var7.hasNext()) {
            EventManager$MethodData var8 = (EventManager$MethodData)var7.next();
            if(var8.getPriority() == var6) {
               var2.add(var8);
            }
         }

         ++var5;
      }

      REGISTRY_MAP.put(var0, var2);
   }

   private static boolean isMethodBad(Method var0) {
      String[] var1 = b();
      return var0.getParameterTypes().length != 1 || !var0.isAnnotationPresent(EventTarget.class);
   }

   private static boolean isMethodBad(Method var0, Class var1) {
      String[] var2 = b();
      return isMethodBad(var0) || !var0.getParameterTypes()[0].equals(var1);
   }

   public static Event call(Event var0) {
      b();
      List var2 = (List)REGISTRY_MAP.get(var0.getClass());
      return var0;
   }

   private static void invoke(EventManager$MethodData var0, Event var1) {
      try {
         var0.getTarget().invoke(var0.getSource(), new Object[]{var1});
      } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException var3) {
         ;
      }

   }

   private static boolean lambda$unregister$1(Object var0, EventManager$MethodData var1) {
      return var1.getSource().equals(var0);
   }

   private static boolean lambda$unregister$0(Object var0, EventManager$MethodData var1) {
      return var1.getSource().equals(var0);
   }

   static {
      b((String[])null);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
