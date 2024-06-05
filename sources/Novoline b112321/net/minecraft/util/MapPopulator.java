package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapPopulator {
   public static Map createMap(Iterable var0, Iterable var1) {
      return populateMap(var0, var1, Maps.newLinkedHashMap());
   }

   public static Map populateMap(Iterable var0, Iterable var1, Map var2) {
      Iterator var3 = var1.iterator();

      for(Object var5 : var0) {
         var2.put(var5, var3.next());
      }

      if(var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return var2;
      }
   }

   private static NoSuchElementException a(NoSuchElementException var0) {
      return var0;
   }
}
