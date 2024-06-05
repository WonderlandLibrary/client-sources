package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$CullState {
   public GlStateManager$BooleanState field_179054_a;
   public int field_179053_b;
   private static final String c = "CL_00002548";

   private GlStateManager$CullState() {
      this.field_179054_a = new GlStateManager$BooleanState(2884);
      this.field_179053_b = 1029;
   }

   GlStateManager$CullState(zY var1) {
      this();
   }
}
