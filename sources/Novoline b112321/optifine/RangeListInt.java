package optifine;

import optifine.Config;
import optifine.MatchBlock;
import optifine.RangeInt;

public class RangeListInt {
   private RangeInt[] ranges = new RangeInt[0];

   public void addRange(RangeInt var1) {
      this.ranges = (RangeInt[])((RangeInt[])((RangeInt[])Config.addObjectToArray(this.ranges, var1)));
   }

   public boolean isInRange(int var1) {
      MatchBlock.b();
      int var3 = 0;
      if(var3 < this.ranges.length) {
         RangeInt var4 = this.ranges[var3];
         if(var4.isInRange(var1)) {
            return true;
         }

         ++var3;
      }

      return false;
   }

   public int getCountRanges() {
      return this.ranges.length;
   }

   public RangeInt getRange(int var1) {
      return this.ranges[var1];
   }

   public String toString() {
      StringBuffer var2 = new StringBuffer();
      var2.append("[");
      MatchBlock.b();
      int var3 = 0;
      if(var3 < this.ranges.length) {
         RangeInt var4 = this.ranges[var3];
         var2.append(", ");
         var2.append(var4.toString());
         ++var3;
      }

      var2.append("]");
      return var2.toString();
   }
}
