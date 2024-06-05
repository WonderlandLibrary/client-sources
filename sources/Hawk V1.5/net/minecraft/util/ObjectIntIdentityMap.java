package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

public class ObjectIntIdentityMap implements IObjectIntIterable {
   private static final String __OBFID = "CL_00001203";
   private final IdentityHashMap field_148749_a = new IdentityHashMap(512);
   private final List field_148748_b = Lists.newArrayList();

   public Iterator iterator() {
      return Iterators.filter(this.field_148748_b.iterator(), Predicates.notNull());
   }

   public List getObjectList() {
      return this.field_148748_b;
   }

   public void put(Object var1, int var2) {
      this.field_148749_a.put(var1, var2);

      while(this.field_148748_b.size() <= var2) {
         this.field_148748_b.add((Object)null);
      }

      this.field_148748_b.set(var2, var1);
   }

   public int get(Object var1) {
      Integer var2 = (Integer)this.field_148749_a.get(var1);
      return var2 == null ? -1 : var2;
   }

   public final Object getByValue(int var1) {
      return var1 >= 0 && var1 < this.field_148748_b.size() ? this.field_148748_b.get(var1) : null;
   }
}
