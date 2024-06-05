package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapPopulator {
   private static final String __OBFID = "CL_00002318";

   public static Map createMap(Iterable var0, Iterable var1) {
      return populateMap(var0, var1, Maps.newLinkedHashMap());
   }

   public static Map populateMap(Iterable var0, Iterable var1, Map var2) {
      Iterator var3 = var1.iterator();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         var2.put(var5, var3.next());
      }

      if (var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return var2;
      }
   }
}
