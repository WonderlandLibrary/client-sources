package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$PolygonOffsetState {
   public GlStateManager$BooleanState field_179044_a;
   public GlStateManager$BooleanState field_179042_b;
   public float field_179043_c;
   public float field_179041_d;
   private static final String c = "CL_00002545";

   private GlStateManager$PolygonOffsetState() {
      this.field_179044_a = new GlStateManager$BooleanState('耷');
      this.field_179042_b = new GlStateManager$BooleanState(10754);
      this.field_179043_c = 0.0F;
      this.field_179041_d = 0.0F;
   }

   GlStateManager$PolygonOffsetState(zY var1) {
      this();
   }
}
