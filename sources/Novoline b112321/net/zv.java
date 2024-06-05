package net;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

class zv implements Callable {
   final EntityRenderer a;
   private static final String b = "CL_00000948";

   zv(EntityRenderer var1) {
      this.a = var1;
   }

   public String a() throws Exception {
      return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
   }
}
