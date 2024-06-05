package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$Color;

class GlStateManager$ClearState {
   int c;
   public double field_179205_a;
   public GlStateManager$Color field_179203_b;
   public int field_179204_c;
   private static final String d = "CL_00002553";

   private GlStateManager$ClearState() {
      this.field_179205_a = 1.0D;
      this.field_179203_b = new GlStateManager$Color(0.0F, 0.0F, 0.0F, 0.0F);
      this.field_179204_c = 0;
      this.c = 114881;
   }

   GlStateManager$ClearState(zY var1) {
      this();
   }
}
