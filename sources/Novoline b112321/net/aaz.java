package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import net.Eb;
import net.Nh;
import net.aaO;
import net.agu;
import net.m6;

public final class aaz {
   private static final Map c = new HashMap();
   private static String[] b;

   public static void b(Object var0) {
      b();
      Method[] var2 = var0.getClass().getDeclaredMethods();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         Method var5 = var2[var4];
         if(a(var5)) {
            ;
         }

         a(var5, var0);
         ++var4;
      }

   }

   public static void b(Object var0, Class var1) {
      b();
      Method[] var3 = var0.getClass().getDeclaredMethods();
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         Method var6 = var3[var5];
         if(a(var6, var1)) {
            ;
         }

         a(var6, var0);
         ++var5;
      }

      if(PacketRemapper.b() == null) {
         b(new String[4]);
      }

   }

   public static void a(Object var0) {
      b();
      Iterator var2 = c.values().iterator();
      if(var2.hasNext()) {
         List var3 = (List)var2.next();
         var3.removeIf(aaz::lambda$unregister$0);
      }

      a(true);
   }

   public static void a(Object var0, Class var1) {
      String[] var2 = b();
      if(c.containsKey(var1)) {
         ((List)c.get(var1)).removeIf(aaz::lambda$unregister$1);
         a(true);
      }

   }

   private static void a(Method var0, Object var1) {
      b();
      Class var3 = var0.getParameterTypes()[0];
      aaO var4 = new aaO(var1, var0, ((agu)var0.getAnnotation(agu.class)).value());
      if(!var4.b().isAccessible()) {
         var4.b().setAccessible(true);
      }

      if(c.containsKey(var3)) {
         if(((List)c.get(var3)).contains(var4)) {
            return;
         }

         ((List)c.get(var3)).add(var4);
         a(var3);
      }

      c.put(var3, new m6(var4));
   }

   public static void b(Class var0) {
      b();
      Iterator var2 = c.entrySet().iterator();

      while(var2.hasNext()) {
         if(((Class)((Entry)var2.next()).getKey()).equals(var0)) {
            var2.remove();
            break;
         }
      }

   }

   public static void a(boolean var0) {
      b();
      Iterator var2 = c.entrySet().iterator();

      while(var2.hasNext()) {
         if(!var0 || ((List)((Entry)var2.next()).getValue()).isEmpty()) {
            var2.remove();
            break;
         }
      }

   }

   private static void a(Class var0) {
      b();
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      byte[] var3 = Nh.f;
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         byte var6 = var3[var5];
         Iterator var7 = ((List)c.get(var0)).iterator();
         if(var7.hasNext()) {
            aaO var8 = (aaO)var7.next();
            if(var8.c() == var6) {
               var2.add(var8);
            }
         }

         ++var5;
      }

      c.put(var0, var2);
   }

   private static boolean a(Method var0) {
      String[] var1 = b();
      return var0.getParameterTypes().length != 1 || !var0.isAnnotationPresent(agu.class);
   }

   private static boolean a(Method var0, Class var1) {
      String[] var2 = b();
      return a(var0) || !var0.getParameterTypes()[0].equals(var1);
   }

   public static Eb a(Eb var0) {
      b();
      List var2 = (List)c.get(var0.getClass());
      return var0;
   }

   private static void a(aaO var0, Eb var1) {
      try {
         var0.b().invoke(var0.a(), new Object[]{var1});
      } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException var3) {
         ;
      }

   }

   private static boolean lambda$unregister$1(Object var0, aaO var1) {
      return var1.a().equals(var0);
   }

   private static boolean lambda$unregister$0(Object var0, aaO var1) {
      return var1.a().equals(var0);
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
