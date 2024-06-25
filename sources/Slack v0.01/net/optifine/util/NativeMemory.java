package net.optifine.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.src.Config;

public class NativeMemory {
   private static LongSupplier bufferAllocatedSupplier = makeLongSupplier(new String[][]{{"sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"}, {"jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"}});
   private static LongSupplier bufferMaximumSupplier = makeLongSupplier(new String[][]{{"sun.misc.VM", "maxDirectMemory"}, {"jdk.internal.misc.VM", "maxDirectMemory"}});

   public static long getBufferAllocated() {
      return bufferAllocatedSupplier == null ? -1L : bufferAllocatedSupplier.getAsLong();
   }

   public static long getBufferMaximum() {
      return bufferMaximumSupplier == null ? -1L : bufferMaximumSupplier.getAsLong();
   }

   private static LongSupplier makeLongSupplier(String[][] paths) {
      List<Throwable> list = new ArrayList();
      int i = 0;

      while(i < paths.length) {
         String[] astring = paths[i];

         try {
            LongSupplier longsupplier = makeLongSupplier(astring);
            return longsupplier;
         } catch (Throwable var5) {
            list.add(var5);
            ++i;
         }
      }

      Iterator var6 = list.iterator();

      while(var6.hasNext()) {
         Throwable throwable1 = (Throwable)var6.next();
         Config.warn("" + throwable1.getClass().getName() + ": " + throwable1.getMessage());
      }

      return null;
   }

   private static LongSupplier makeLongSupplier(String[] path) throws Exception {
      if (path.length < 2) {
         return null;
      } else {
         Class oclass = Class.forName(path[0]);
         final Method method = oclass.getMethod(path[1]);
         method.setAccessible(true);
         final Object object = null;

         for(int i = 2; i < path.length; ++i) {
            String s = path[i];
            object = method.invoke(object);
            method = object.getClass().getMethod(s);
            method.setAccessible(true);
         }

         LongSupplier longsupplier = new LongSupplier() {
            private boolean disabled = false;

            public long getAsLong() {
               if (this.disabled) {
                  return -1L;
               } else {
                  try {
                     return (Long)method.invoke(object);
                  } catch (Throwable var2) {
                     Config.warn("" + var2.getClass().getName() + ": " + var2.getMessage());
                     this.disabled = true;
                     return -1L;
                  }
               }
            }
         };
         return longsupplier;
      }
   }
}
