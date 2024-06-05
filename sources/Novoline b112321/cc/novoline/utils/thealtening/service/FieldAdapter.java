package cc.novoline.utils.thealtening.service;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import net.af_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FieldAdapter {
   private static final Logger LOGGER = LogManager.getLogger();
   private final HashMap fields = new HashMap();
   private static final Lookup LOOKUP;
   private static Field MODIFIERS;

   public FieldAdapter(String var1) {
      try {
         Class var2 = af_.a(var1);
         Field var3 = MODIFIERS;

         for(Field var7 : var2.getDeclaredFields()) {
            var7.setAccessible(true);
            int var8 = var7.getModifiers();
            if(Modifier.isFinal(var8)) {
               var3.setInt(var7, var8 & -17);
            }

            MethodHandle var9 = LOOKUP.unreflectSetter(var7);
            var9 = var9.asType(var9.type().generic().changeReturnType(Void.TYPE));
            this.fields.put(var7.getName(), var9);
         }

      } catch (ClassNotFoundException var10) {
         throw new RuntimeException("Couldn\'t load/find the specified class");
      } catch (Exception var11) {
         throw new RuntimeException("Couldn\'t create a method handler for the field");
      }
   }

   public void updateFieldIfPresent(String var1, Object var2) {
      Optional.ofNullable(this.fields.get(var1)).ifPresent(FieldAdapter::lambda$updateFieldIfPresent$0);
   }

   private static void lambda$updateFieldIfPresent$0(Object var0, MethodHandle var1) {
      MethodHandle var10000 = var1;
      Object var10001 = var0;

      try {
         var10000.invokeExact(var10001);
      } catch (Throwable var3) {
         LOGGER.warn(var3);
      }

   }

   static {
      try {
         MODIFIERS = af_.c(Field.class, "modifiers");
         MODIFIERS.setAccessible(true);
      } catch (NoSuchFieldException var10) {
         LOGGER.warn(var10);
      }

      Lookup var7;
      try {
         Field var8 = af_.c(Lookup.class, "IMPL_LOOKUP");
         var8.setAccessible(true);
         var7 = (Lookup)var8.get((Object)null);
      } catch (ReflectiveOperationException var9) {
         var7 = MethodHandles.lookup();
      }

      LOOKUP = var7;
   }

   private static ClassNotFoundException a(ClassNotFoundException var0) {
      return var0;
   }
}
