package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$TexGenCoord;

class GlStateManager$TexGenState {
   public GlStateManager$TexGenCoord field_179064_a;
   public GlStateManager$TexGenCoord field_179062_b;
   public GlStateManager$TexGenCoord field_179063_c;
   public GlStateManager$TexGenCoord field_179061_d;
   private static final String c = "CL_00002540";

   private GlStateManager$TexGenState() {
      this.field_179064_a = new GlStateManager$TexGenCoord(8192, 3168);
      this.field_179062_b = new GlStateManager$TexGenCoord(8193, 3169);
      this.field_179063_c = new GlStateManager$TexGenCoord(8194, 3170);
      this.field_179061_d = new GlStateManager$TexGenCoord(8195, 3171);
   }

   GlStateManager$TexGenState(zY var1) {
      this();
   }
}
