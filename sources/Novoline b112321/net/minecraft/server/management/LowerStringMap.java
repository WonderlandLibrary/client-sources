package net.minecraft.server.management;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class LowerStringMap implements Map {
   private final Map internalMap = Maps.newLinkedHashMap();

   public int size() {
      return this.internalMap.size();
   }

   public boolean isEmpty() {
      return this.internalMap.isEmpty();
   }

   public boolean containsKey(Object var1) {
      return this.internalMap.containsKey(var1.toString().toLowerCase());
   }

   public boolean containsValue(Object var1) {
      return this.internalMap.containsKey(var1);
   }

   public Object get(Object var1) {
      return this.internalMap.get(var1.toString().toLowerCase());
   }

   public Object put(String var1, Object var2) {
      return this.internalMap.put(var1.toLowerCase(), var2);
   }

   public Object remove(Object var1) {
      return this.internalMap.remove(var1.toString().toLowerCase());
   }

   public void putAll(Map var1) {
      for(Entry var3 : var1.entrySet()) {
         this.put((String)var3.getKey(), var3.getValue());
      }

   }

   public void clear() {
      this.internalMap.clear();
   }

   public Set keySet() {
      return this.internalMap.keySet();
   }

   public Collection values() {
      return this.internalMap.values();
   }

   public Set entrySet() {
      return this.internalMap.entrySet();
   }
}
