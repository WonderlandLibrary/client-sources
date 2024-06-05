package net.optifine;

import java.util.ArrayList;
import net.acE;
import net.optifine.MatchBlock;

public class CompactArrayList {
   private ArrayList list;
   private int initialCapacity;
   private float loadFactor;
   private int countValid;

   public CompactArrayList() {
      this(10, 0.75F);
   }

   public CompactArrayList(int var1) {
      this(var1, 0.75F);
   }

   public CompactArrayList(int var1, float var2) {
      this.list = null;
      this.initialCapacity = 0;
      this.loadFactor = 1.0F;
      this.countValid = 0;
      this.list = new ArrayList(var1);
      this.initialCapacity = var1;
      this.loadFactor = var2;
   }

   public void add(int var1, Object var2) {
      acE[] var3 = MatchBlock.b();
      ++this.countValid;
      this.list.add(var1, var2);
   }

   public boolean add(Object var1) {
      ++this.countValid;
      return this.list.add(var1);
   }

   public Object set(int var1, Object var2) {
      MatchBlock.b();
      Object var4 = this.list.set(var1, var2);
      if(var2 != var4) {
         if(var4 == null) {
            ++this.countValid;
         }

         if(var2 == null) {
            --this.countValid;
         }
      }

      return var4;
   }

   public Object remove(int var1) {
      MatchBlock.b();
      Object var3 = this.list.remove(var1);
      if(var3 != null) {
         --this.countValid;
      }

      return var3;
   }

   public void clear() {
      this.list.clear();
      this.countValid = 0;
   }

   public void compact() {
      acE[] var1 = MatchBlock.b();
      if(this.countValid <= 0 && this.list.size() <= 0) {
         this.clear();
      }

      if(this.list.size() > this.initialCapacity) {
         float var2 = (float)this.countValid * 1.0F / (float)this.list.size();
         if(var2 <= this.loadFactor) {
            int var3 = 0;
            int var4 = 0;
            if(var4 < this.list.size()) {
               Object var5 = this.list.get(var4);
               if(var5 != null) {
                  if(var4 != var3) {
                     this.list.set(var3, var5);
                  }

                  ++var3;
               }

               ++var4;
            }

            var4 = this.list.size() - 1;
            if(var4 >= var3) {
               this.list.remove(var4);
               --var4;
            }
         }
      }

   }

   public boolean contains(Object var1) {
      return this.list.contains(var1);
   }

   public Object get(int var1) {
      return this.list.get(var1);
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public int size() {
      return this.list.size();
   }

   public int getCountValid() {
      return this.countValid;
   }
}
