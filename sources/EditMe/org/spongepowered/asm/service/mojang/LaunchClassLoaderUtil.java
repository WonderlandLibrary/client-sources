package org.spongepowered.asm.service.mojang;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.launchwrapper.LaunchClassLoader;

final class LaunchClassLoaderUtil {
   private static final String CACHED_CLASSES_FIELD = "cachedClasses";
   private static final String INVALID_CLASSES_FIELD = "invalidClasses";
   private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
   private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
   private final LaunchClassLoader classLoader;
   private final Map cachedClasses;
   private final Set invalidClasses;
   private final Set classLoaderExceptions;
   private final Set transformerExceptions;

   LaunchClassLoaderUtil(LaunchClassLoader var1) {
      this.classLoader = var1;
      this.cachedClasses = (Map)getField(var1, "cachedClasses");
      this.invalidClasses = (Set)getField(var1, "invalidClasses");
      this.classLoaderExceptions = (Set)getField(var1, "classLoaderExceptions");
      this.transformerExceptions = (Set)getField(var1, "transformerExceptions");
   }

   LaunchClassLoader getClassLoader() {
      return this.classLoader;
   }

   boolean isClassLoaded(String var1) {
      return this.cachedClasses.containsKey(var1);
   }

   boolean isClassExcluded(String var1, String var2) {
      Iterator var3 = this.getClassLoaderExceptions().iterator();

      String var4;
      do {
         if (!var3.hasNext()) {
            var3 = this.getTransformerExceptions().iterator();

            do {
               if (!var3.hasNext()) {
                  return false;
               }

               var4 = (String)var3.next();
            } while(!var2.startsWith(var4) && !var1.startsWith(var4));

            return true;
         }

         var4 = (String)var3.next();
      } while(!var2.startsWith(var4) && !var1.startsWith(var4));

      return true;
   }

   void registerInvalidClass(String var1) {
      if (this.invalidClasses != null) {
         this.invalidClasses.add(var1);
      }

   }

   Set getClassLoaderExceptions() {
      return this.classLoaderExceptions != null ? this.classLoaderExceptions : Collections.emptySet();
   }

   Set getTransformerExceptions() {
      return this.transformerExceptions != null ? this.transformerExceptions : Collections.emptySet();
   }

   private static Object getField(LaunchClassLoader var0, String var1) {
      try {
         Field var2 = LaunchClassLoader.class.getDeclaredField(var1);
         var2.setAccessible(true);
         return var2.get(var0);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }
}
