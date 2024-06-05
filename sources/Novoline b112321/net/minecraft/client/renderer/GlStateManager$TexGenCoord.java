package net.minecraft.client.renderer;

import net.minecraft.client.renderer.GlStateManager$BooleanState;

class GlStateManager$TexGenCoord {
   public GlStateManager$BooleanState field_179067_a;
   public int field_179065_b;
   public int field_179066_c = -1;
   private static final String a = "CL_00002541";

   public GlStateManager$TexGenCoord(int var1, int var2) {
      this.field_179065_b = var1;
      this.field_179067_a = new GlStateManager$BooleanState(var2);
   }
}
