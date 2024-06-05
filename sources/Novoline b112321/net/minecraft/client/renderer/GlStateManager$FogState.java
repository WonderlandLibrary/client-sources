package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$FogState {
   public GlStateManager$BooleanState field_179049_a;
   public int field_179047_b;
   public float field_179048_c;
   public float field_179045_d;
   public float field_179046_e;
   private static final String e = "CL_00002546";

   private GlStateManager$FogState() {
      this.field_179049_a = new GlStateManager$BooleanState(2912);
      this.field_179047_b = 2048;
      this.field_179048_c = 1.0F;
      this.field_179045_d = 0.0F;
      this.field_179046_e = 1.0F;
   }

   GlStateManager$FogState(zY var1) {
      this();
   }
}
