package net.minecraft.util;

public class Tuple {
   private static final String __OBFID = "CL_00001502";
   private Object a;
   private Object b;

   public Object getFirst() {
      return this.a;
   }

   public Tuple(Object var1, Object var2) {
      this.a = var1;
      this.b = var2;
   }

   public Object getSecond() {
      return this.b;
   }
}
