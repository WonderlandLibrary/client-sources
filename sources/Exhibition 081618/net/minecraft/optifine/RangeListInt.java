package net.minecraft.optifine;

public class RangeListInt {
   private RangeInt[] ranges = new RangeInt[0];

   public void addRange(RangeInt ri) {
      this.ranges = (RangeInt[])((RangeInt[])Config.addObjectToArray(this.ranges, ri));
   }

   public boolean isInRange(int val) {
      RangeInt[] var2 = this.ranges;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         RangeInt ri = var2[var4];
         if (ri.isInRange(val)) {
            return true;
         }
      }

      return false;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[");

      for(int i = 0; i < this.ranges.length; ++i) {
         RangeInt ri = this.ranges[i];
         if (i > 0) {
            sb.append(", ");
         }

         sb.append(ri.toString());
      }

      sb.append("]");
      return sb.toString();
   }
}
