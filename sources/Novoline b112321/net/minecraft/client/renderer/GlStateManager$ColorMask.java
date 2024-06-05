package net.minecraft.client.renderer;

import net.zY;

class GlStateManager$ColorMask {
   public boolean red;
   public boolean green;
   public boolean blue;
   public boolean alpha;
   private static final String b = "CL_00002550";

   private GlStateManager$ColorMask() {
      this.red = true;
      this.green = true;
      this.blue = true;
      this.alpha = true;
   }

   GlStateManager$ColorMask(zY var1) {
      this();
   }
}
