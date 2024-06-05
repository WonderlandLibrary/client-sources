package net.minecraft.util;

import net.minecraft.util.LongHashMap;

class LongHashMap$Entry {
   final long key;
   Object value;
   LongHashMap$Entry nextEntry;
   final int hash;
   private static final String a = "CL_00001493";

   LongHashMap$Entry(int var1, long var2, Object var4, LongHashMap$Entry var5) {
      this.value = var4;
      this.nextEntry = var5;
      this.key = var2;
      this.hash = var1;
   }

   public final long getKey() {
      return this.key;
   }

   public final Object getValue() {
      return this.value;
   }

   public final boolean equals(Object var1) {
      if(var1 instanceof LongHashMap$Entry) {
         LongHashMap$Entry var2 = (LongHashMap$Entry)var1;
         Long var3 = Long.valueOf(this.getKey());
         Long var4 = Long.valueOf(var2.getKey());
         if(var3 == var4 || var3.equals(var4)) {
            Object var5 = this.getValue();
            Object var6 = var2.getValue();
            return var5 == var6 || var5.equals(var6);
         }
      }

      return false;
   }

   public final int hashCode() {
      return LongHashMap.access$000(this.key);
   }

   public final String toString() {
      return this.getKey() + "=" + this.getValue();
   }
}
