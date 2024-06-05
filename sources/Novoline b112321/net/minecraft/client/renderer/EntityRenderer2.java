package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

class EntityRenderer2 implements Callable {
   final EntityRenderer field_90025_c;
   private static final String b = "CL_00000948";

   EntityRenderer2(EntityRenderer var1) {
      this.field_90025_c = var1;
   }

   public String call() throws Exception {
      return Minecraft.getInstance().currentScreen.getClass().getCanonicalName();
   }
}
