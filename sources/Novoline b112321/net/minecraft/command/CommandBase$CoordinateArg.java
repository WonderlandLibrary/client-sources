package net.minecraft.command;

public class CommandBase$CoordinateArg {
   private final double field_179633_a;
   private final double field_179631_b;
   private final boolean field_179632_c;

   protected CommandBase$CoordinateArg(double var1, double var3, boolean var5) {
      this.field_179633_a = var1;
      this.field_179631_b = var3;
      this.field_179632_c = var5;
   }

   public double func_179628_a() {
      return this.field_179633_a;
   }

   public double func_179629_b() {
      return this.field_179631_b;
   }

   public boolean func_179630_c() {
      return this.field_179632_c;
   }
}
