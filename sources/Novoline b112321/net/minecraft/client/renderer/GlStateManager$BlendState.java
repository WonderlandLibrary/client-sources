package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$BlendState {
   public GlStateManager$BooleanState field_179213_a;
   public int srcFactor;
   public int dstFactor;
   public int srcFactorAlpha;
   public int dstFactorAlpha;
   private static final String c = "CL_00002555";

   private GlStateManager$BlendState() {
      this.field_179213_a = new GlStateManager$BooleanState(3042);
      this.srcFactor = 1;
      this.dstFactor = 0;
      this.srcFactorAlpha = 1;
      this.dstFactorAlpha = 0;
   }

   GlStateManager$BlendState(zY var1) {
      this();
   }
}
