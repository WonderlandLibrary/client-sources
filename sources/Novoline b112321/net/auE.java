package net;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;

public class auE {
   private static String[] b;

   public static void a(int var0, int var1, int var2, float var3, float var4, EntityLivingBase var5) {
      GuiInventory.drawEntityOnScreen(var0, var1, var2, var3, var4, var5);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[3]);
      }

   }
}
