package net.minecraft.util;

public class Tuple {
   private Object a;
   private Object b;

   public Tuple(Object p_i1555_1_, Object p_i1555_2_) {
      this.a = p_i1555_1_;
      this.b = p_i1555_2_;
   }

   public Object getFirst() {
      return this.a;
   }

   public Object getSecond() {
      return this.b;
   }
}
