package optifine;

import optifine.MatchBlock;

public class IntArray {
   private int[] array = null;
   private int position = 0;
   private int limit = 0;

   public IntArray(int var1) {
      this.array = new int[var1];
   }

   public void put(int var1) {
      MatchBlock.b();
      this.array[this.position] = var1;
      ++this.position;
      if(this.limit < this.position) {
         this.limit = this.position;
      }

   }

   public void put(int var1, int var2) {
      MatchBlock.b();
      this.array[var1] = var2;
      if(this.limit < var1) {
         this.limit = var1;
      }

   }

   public void position(int var1) {
      this.position = var1;
   }

   public void put(int[] var1) {
      MatchBlock.b();
      int var3 = var1.length;
      int var4 = 0;
      if(var4 < var3) {
         this.array[this.position] = var1[var4];
         ++this.position;
         ++var4;
      }

      if(this.limit < this.position) {
         this.limit = this.position;
      }

   }

   public int get(int var1) {
      return this.array[var1];
   }

   public int[] getArray() {
      return this.array;
   }

   public void clear() {
      this.position = 0;
      this.limit = 0;
   }

   public int getLimit() {
      return this.limit;
   }

   public int getPosition() {
      return this.position;
   }
}
