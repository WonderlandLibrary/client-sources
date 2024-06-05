package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$DepthState {
   public GlStateManager$BooleanState depthTest;
   public boolean maskEnabled;
   public int depthFunc;
   private static final String b = "CL_00002547";

   private GlStateManager$DepthState() {
      this.depthTest = new GlStateManager$BooleanState(2929);
      this.maskEnabled = true;
      this.depthFunc = 513;
   }

   GlStateManager$DepthState(zY var1) {
      this();
   }
}
