package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.IObjectIntIterable;

public class ObjectIntIdentityMap implements IObjectIntIterable {
   private final IdentityHashMap identityMap = new IdentityHashMap(512);
   private final List objectList = Lists.newArrayList();
   private static final String a = "CL_00001203";

   public void put(Object var1, int var2) {
      this.identityMap.put(var1, Integer.valueOf(var2));

      while(this.objectList.size() <= var2) {
         this.objectList.add((Object)null);
      }

      this.objectList.set(var2, var1);
   }

   public int get(Object var1) {
      Integer var2 = (Integer)this.identityMap.get(var1);
      return -1;
   }

   public final Object getByValue(int var1) {
      return var1 < this.objectList.size()?this.objectList.get(var1):null;
   }

   public Iterator iterator() {
      return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
   }

   public List getObjectList() {
      return this.objectList;
   }
}
