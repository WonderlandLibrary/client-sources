package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$AlphaState {
   public GlStateManager$BooleanState field_179208_a;
   public int func;
   public float ref;
   private static final String c = "CL_00002556";

   private GlStateManager$AlphaState() {
      this.field_179208_a = new GlStateManager$BooleanState(3008);
      this.func = 519;
      this.ref = -1.0F;
   }

   GlStateManager$AlphaState(zY var1) {
      this();
   }
}
