package ninja.leaping.configurate;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import net.acE;
import ninja.leaping.configurate.ConfigValue;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.ValueType;

class MapConfigValue extends ConfigValue {
   volatile ConcurrentMap values = this.newMap();

   public MapConfigValue(SimpleConfigurationNode var1) {
      super(var1);
   }

   ValueType getType() {
      return ValueType.MAP;
   }

   private ConcurrentMap newMap() {
      return this.holder.getOptions().getMapFactory().create();
   }

   public Object getValue() {
      LinkedHashMap var2 = new LinkedHashMap();
      ValueType.b();
      Iterator var3 = this.values.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         var2.put(var4.getKey(), ((SimpleConfigurationNode)var4.getValue()).getValue());
      }

      return var2;
   }

   public void setValue(Object param1) {
      // $FF: Couldn't be decompiled
   }

   SimpleConfigurationNode putChild(Object var1, SimpleConfigurationNode var2) {
      acE[] var3 = ValueType.b();
      return var2 == null?(SimpleConfigurationNode)this.values.remove(var1):(SimpleConfigurationNode)this.values.put(var1, var2);
   }

   SimpleConfigurationNode putChildIfAbsent(Object var1, SimpleConfigurationNode var2) {
      acE[] var3 = ValueType.b();
      return var2 == null?(SimpleConfigurationNode)this.values.remove(var1):(SimpleConfigurationNode)this.values.putIfAbsent(var1, var2);
   }

   public SimpleConfigurationNode getChild(Object var1) {
      return (SimpleConfigurationNode)this.values.get(var1);
   }

   public Iterable iterateChildren() {
      return this.values.values();
   }

   MapConfigValue copy(SimpleConfigurationNode var1) {
      MapConfigValue var3 = new MapConfigValue(var1);
      ValueType.b();
      Iterator var4 = this.values.entrySet().iterator();
      if(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         var3.values.put(var5.getKey(), ((SimpleConfigurationNode)var5.getValue()).copy(var1));
      }

      return var3;
   }

   private static void detachChildren(Map var0) {
      ValueType.b();
      Iterator var2 = var0.values().iterator();
      if(var2.hasNext()) {
         SimpleConfigurationNode var3 = (SimpleConfigurationNode)var2.next();
         var3.attached = false;
         var3.clear();
      }

   }

   public void clear() {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      acE[] var2 = ValueType.b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         MapConfigValue var3 = (MapConfigValue)var1;
         return Objects.equals(this.values, var3.values);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.values.hashCode();
   }

   public String toString() {
      return "MapConfigValue{values=" + this.values + '}';
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
