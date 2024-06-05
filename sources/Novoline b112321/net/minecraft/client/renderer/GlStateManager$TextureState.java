package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$TextureState {
   public GlStateManager$BooleanState texture2DState;
   public int textureName;
   private static final String a = "CL_00002539";

   private GlStateManager$TextureState() {
      this.texture2DState = new GlStateManager$BooleanState(3553);
      this.textureName = 0;
   }

   GlStateManager$TextureState(zY var1) {
      this();
   }
}
