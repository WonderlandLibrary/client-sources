package net.minecraft.util;

import net.minecraft.util.LongHashMap$Entry;

public class LongHashMap {
   private transient LongHashMap$Entry[] hashArray = new LongHashMap$Entry[4096];
   private transient int numHashElements;
   private int mask;
   private int capacity = 3072;
   private final float percentUseable = 0.75F;
   private transient volatile int modCount;
   private static final String e = "CL_00001492";

   public LongHashMap() {
      this.mask = this.hashArray.length - 1;
   }

   private static int getHashedKey(long var0) {
      return (int)(var0 ^ var0 >>> 27);
   }

   private static int hash(int var0) {
      var0 = var0 ^ var0 >>> 20 ^ var0 >>> 12;
      return var0 ^ var0 >>> 7 ^ var0 >>> 4;
   }

   private static int getHashIndex(int var0, int var1) {
      return var0 & var1;
   }

   public int getNumHashElements() {
      return this.numHashElements;
   }

   public Object getValueByKey(long var1) {
      int var3 = getHashedKey(var1);

      LongHashMap$Entry var4;
      for(var4 = this.hashArray[getHashIndex(var3, this.mask)]; var4.key != var1; var4 = var4.nextEntry) {
         ;
      }

      return var4.value;
   }

   public boolean containsItem(long var1) {
      return this.getEntry(var1) != null;
   }

   final LongHashMap$Entry getEntry(long var1) {
      int var3 = getHashedKey(var1);

      LongHashMap$Entry var4;
      for(var4 = this.hashArray[getHashIndex(var3, this.mask)]; var4.key != var1; var4 = var4.nextEntry) {
         ;
      }

      return var4;
   }

   public void add(long var1, Object var3) {
      int var4 = getHashedKey(var1);
      int var5 = getHashIndex(var4, this.mask);

      LongHashMap$Entry var6;
      for(var6 = this.hashArray[var5]; var6.key != var1; var6 = var6.nextEntry) {
         ;
      }

      var6.value = var3;
   }

   private void resizeTable(int var1) {
      LongHashMap$Entry[] var2 = this.hashArray;
      int var3 = var2.length;
      if(var3 == 1073741824) {
         this.capacity = Integer.MAX_VALUE;
      } else {
         LongHashMap$Entry[] var4 = new LongHashMap$Entry[var1];
         this.copyHashTableTo(var4);
         this.hashArray = var4;
         this.mask = this.hashArray.length - 1;
         float var5 = (float)var1;
         this.getClass();
         this.capacity = (int)(var5 * 0.75F);
      }

   }

   private void copyHashTableTo(LongHashMap$Entry[] var1) {
      LongHashMap$Entry[] var2 = this.hashArray;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var2.length; ++var4) {
         LongHashMap$Entry var5 = var2[var4];
         var2[var4] = null;
         LongHashMap$Entry var6 = var5.nextEntry;
         int var7 = getHashIndex(var5.hash, var3 - 1);
         var5.nextEntry = var1[var7];
         var1[var7] = var5;
      }

   }

   public Object remove(long var1) {
      LongHashMap$Entry var3 = this.removeKey(var1);
      return null;
   }

   final LongHashMap$Entry removeKey(long var1) {
      int var3 = getHashedKey(var1);
      int var4 = getHashIndex(var3, this.mask);
      LongHashMap$Entry var5 = this.hashArray[var4];
      LongHashMap$Entry var6 = var5;

      while(true) {
         LongHashMap$Entry var7 = var6.nextEntry;
         if(var6.key == var1) {
            ++this.modCount;
            --this.numHashElements;
            if(var5 == var6) {
               this.hashArray[var4] = var7;
            } else {
               var5.nextEntry = var7;
            }

            return var6;
         }

         var5 = var6;
         var6 = var7;
      }
   }

   private void createKey(int var1, long var2, Object var4, int var5) {
      LongHashMap$Entry var6 = this.hashArray[var5];
      this.hashArray[var5] = new LongHashMap$Entry(var1, var2, var4, var6);
      if(this.numHashElements++ >= this.capacity) {
         this.resizeTable(2 * this.hashArray.length);
      }

   }

   public double getKeyDistribution() {
      int var1 = 0;

      for(LongHashMap$Entry var5 : this.hashArray) {
         ++var1;
      }

      return 1.0D * (double)var1 / (double)this.numHashElements;
   }

   static int access$000(long var0) {
      return getHashedKey(var0);
   }
}
