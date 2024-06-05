package net.minecraft.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistrySimple;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable {
   protected final ObjectIntIdentityMap underlyingIntegerMap = new ObjectIntIdentityMap();
   protected final Map inverseObjectRegistry;

   public RegistryNamespaced() {
      this.inverseObjectRegistry = ((BiMap)this.registryObjects).inverse();
   }

   public void register(int var1, Object var2, Object var3) {
      this.underlyingIntegerMap.put(var3, var1);
      this.putObject(var2, var3);
   }

   protected Map createUnderlyingMap() {
      return HashBiMap.create();
   }

   public Object getObject(Object var1) {
      return super.getObject(var1);
   }

   public Object getNameForObject(Object var1) {
      return this.inverseObjectRegistry.get(var1);
   }

   public boolean containsKey(Object var1) {
      return super.containsKey(var1);
   }

   public int getIDForObject(Object var1) {
      return this.underlyingIntegerMap.get(var1);
   }

   public Object getObjectById(int var1) {
      return this.underlyingIntegerMap.getByValue(var1);
   }

   public Iterator iterator() {
      return this.underlyingIntegerMap.iterator();
   }
}
