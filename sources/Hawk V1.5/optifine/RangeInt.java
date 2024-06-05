package optifine;

public class RangeInt {
   private int min;
   private int max;

   public int getMax() {
      return this.max;
   }

   public boolean isInRange(int var1) {
      return var1 < this.min ? false : var1 <= this.max;
   }

   public int getMin() {
      return this.min;
   }

   public RangeInt(int var1, int var2) {
      this.min = Math.min(var1, var2);
      this.max = Math.max(var1, var2);
   }

   public String toString() {
      return String.valueOf((new StringBuilder("min: ")).append(this.min).append(", max: ").append(this.max));
   }
}
