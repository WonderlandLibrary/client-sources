package net.minecraft.client.renderer;

import net.zY;
import net.minecraft.client.renderer.GlStateManager$StencilFunc;

class GlStateManager$StencilState {
   public GlStateManager$StencilFunc field_179078_a;
   public int field_179076_b;
   public int field_179077_c;
   public int field_179074_d;
   public int field_179075_e;
   private static final String a = "CL_00002543";

   private GlStateManager$StencilState() {
      this.field_179078_a = new GlStateManager$StencilFunc((zY)null);
      this.field_179076_b = -1;
      this.field_179077_c = 7680;
      this.field_179074_d = 7680;
      this.field_179075_e = 7680;
   }

   GlStateManager$StencilState(zY var1) {
      this();
   }
}
