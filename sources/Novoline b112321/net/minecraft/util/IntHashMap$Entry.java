package net.minecraft.util;

import net.minecraft.util.IntHashMap;

class IntHashMap$Entry {
   final int hashEntry;
   Object valueEntry;
   IntHashMap$Entry nextEntry;
   final int slotHash;

   IntHashMap$Entry(int var1, int var2, Object var3, IntHashMap$Entry var4) {
      this.valueEntry = var3;
      this.nextEntry = var4;
      this.hashEntry = var2;
      this.slotHash = var1;
   }

   public final int getHash() {
      return this.hashEntry;
   }

   public final Object getValue() {
      return this.valueEntry;
   }

   public final boolean equals(Object var1) {
      if(var1 instanceof IntHashMap$Entry) {
         IntHashMap$Entry var2 = (IntHashMap$Entry)var1;
         Integer var3 = Integer.valueOf(this.getHash());
         Integer var4 = Integer.valueOf(var2.getHash());
         if(var3 == var4 || var3.equals(var4)) {
            Object var5 = this.getValue();
            Object var6 = var2.getValue();
            return var5 == var6 || var5.equals(var6);
         }
      }

      return false;
   }

   public final int hashCode() {
      return IntHashMap.access$000(this.hashEntry);
   }

   public final String toString() {
      return this.getHash() + "=" + this.getValue();
   }
}
