package net.minecraft.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import java.util.Map;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable {
   private static final String __OBFID = "CL_00001206";
   protected final ObjectIntIdentityMap underlyingIntegerMap = new ObjectIntIdentityMap();
   protected final Map field_148758_b;

   public Object getNameForObject(Object var1) {
      return this.field_148758_b.get(var1);
   }

   public Iterator iterator() {
      return this.underlyingIntegerMap.iterator();
   }

   public RegistryNamespaced() {
      this.field_148758_b = ((BiMap)this.registryObjects).inverse();
   }

   public Object getObject(Object var1) {
      return super.getObject(var1);
   }

   public void register(int var1, Object var2, Object var3) {
      this.underlyingIntegerMap.put(var3, var1);
      this.putObject(var2, var3);
   }

   public boolean containsKey(Object var1) {
      return super.containsKey(var1);
   }

   protected Map createUnderlyingMap() {
      return HashBiMap.create();
   }

   public Object getObjectById(int var1) {
      return this.underlyingIntegerMap.getByValue(var1);
   }

   public int getIDForObject(Object var1) {
      return this.underlyingIntegerMap.get(var1);
   }
}
