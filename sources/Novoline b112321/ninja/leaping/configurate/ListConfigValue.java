package ninja.leaping.configurate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import net.acE;
import ninja.leaping.configurate.ConfigValue;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.ValueType;

class ListConfigValue extends ConfigValue {
   final AtomicReference values = new AtomicReference(new ArrayList());

   ListConfigValue(SimpleConfigurationNode var1) {
      super(var1);
   }

   ValueType getType() {
      return ValueType.LIST;
   }

   ListConfigValue(SimpleConfigurationNode var1, Object var2) {
      super(var1);
      SimpleConfigurationNode var3 = var1.createNode(Integer.valueOf(0));
      var3.attached = true;
      var3.setValue(var2);
      ((List)this.values.get()).add(var3);
   }

   public Object getValue() {
      // $FF: Couldn't be decompiled
   }

   public void setValue(Object var1) {
      acE[] var2 = ValueType.b();
      if(!(var1 instanceof Collection)) {
         var1 = Collections.singleton(var1);
      }

      Collection var3 = (Collection)var1;
      ArrayList var4 = new ArrayList(var3.size());
      int var5 = 0;
      Iterator var6 = var3.iterator();
      if(var6.hasNext()) {
         Object var7 = var6.next();
         if(var7 == null) {
            ;
         }

         SimpleConfigurationNode var8 = this.holder.createNode(Integer.valueOf(var5));
         var4.add(var5, var8);
         var8.attached = true;
         var8.setValue(var7);
         ++var5;
      }

      detachNodes((List)this.values.getAndSet(var4));
   }

   public SimpleConfigurationNode putChild(Object var1, SimpleConfigurationNode var2) {
      return this.putChild(((Integer)var1).intValue(), var2, false);
   }

   SimpleConfigurationNode putChildIfAbsent(Object var1, SimpleConfigurationNode var2) {
      return this.putChild(((Integer)var1).intValue(), var2, true);
   }

   private SimpleConfigurationNode putChild(int param1, SimpleConfigurationNode param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   public SimpleConfigurationNode getChild(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public Iterable iterateChildren() {
      // $FF: Couldn't be decompiled
   }

   ListConfigValue copy(SimpleConfigurationNode param1) {
      // $FF: Couldn't be decompiled
   }

   private static void detachNodes(List param0) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      List var1 = (List)this.values.getAndSet(new ArrayList());
      detachNodes(var1);
   }

   public boolean equals(Object var1) {
      acE[] var2 = ValueType.b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         ListConfigValue var3 = (ListConfigValue)var1;
         return Objects.equals(this.values.get(), var3.values.get());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return ((List)this.values.get()).hashCode();
   }

   public String toString() {
      return "ListConfigValue{values=" + ((List)this.values.get()).toString() + '}';
   }
}
