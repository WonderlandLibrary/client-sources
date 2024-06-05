package net;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.X9;
import net.aBM;
import net.acK;
import net.axb;
import net.lb;
import org.jetbrains.annotations.NotNull;

public class up {
   protected final Class b;
   private final Constructor c;
   private final Map a = new LinkedHashMap();

   public static up a(@NotNull Class var0) throws X9 {
      return acK.a().a(var0);
   }

   public static axb b(@NotNull Object var0) throws X9 {
      return a(Objects.requireNonNull(var0).getClass()).a(var0);
   }

   protected up(Class var1) throws X9 {
      X9.b();
      this.b = var1;
      Constructor var3 = null;

      try {
         var3 = var1.getDeclaredConstructor(new Class[0]);
         var3.setAccessible(true);
      } catch (NoSuchMethodException var5) {
         ;
      }

      this.c = var3;
      Class var4 = var1;

      while(true) {
         this.a(this.a, var4);
         if((var4 = var4.getSuperclass()).equals(Object.class)) {
            break;
         }
      }

   }

   public Class d() {
      return this.b;
   }

   protected void a(Map var1, Class var2) throws X9 {
      X9.b();
      Field[] var4 = var2.getDeclaredFields();
      int var5 = var4.length;
      int var6 = 0;
      if(var6 < var5) {
         Field var7 = var4[var6];
         if(var7.isAnnotationPresent(lb.class)) {
            lb var8 = (lb)var7.getAnnotation(lb.class);
            String var9 = var8.value();
            if(var9.isEmpty()) {
               throw new X9("Empty path");
            }

            aBM var10 = new aBM(var7, var8.a());
            var7.setAccessible(true);
            if(!var1.containsKey(var9)) {
               var1.put(var9, var10);
            }
         }

         ++var6;
      }

   }

   protected Object a() throws X9 {
      int[] var1 = X9.b();
      if(this.c == null) {
         throw new X9("No zero-arg constructor is available for class " + this.b + " but is required to construct new instances!");
      } else {
         try {
            return this.c.newInstance(new Object[0]);
         } catch (IllegalAccessException | InvocationTargetException | InstantiationException var3) {
            throw new X9("Unable to create instance of target class " + this.b, var3);
         }
      }
   }

   public boolean c() {
      return this.c != null;
   }

   public axb a(Object var1) {
      return new axb(this, var1);
   }

   public axb b() throws X9 {
      return new axb(this, this.a());
   }

   public Class e() {
      return this.b;
   }

   static Map a(up var0) {
      return var0.a;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
