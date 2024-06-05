package net;

import net.aHz;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBeacon$Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;

class aZx extends GuiBeacon$Button {
   private final int v;
   private final int u;
   final GuiBeacon t;

   public aZx(GuiBeacon var1, int var2, int var3, int var4, int var5, int var6) {
      super(var2, var3, var4, aHz.v, Potion.potionTypes[var5].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[var5].getStatusIconIndex() / 8 * 18);
      this.t = var1;
      this.v = var5;
      this.u = var6;
   }

   public void drawButtonForegroundLayer(int var1, int var2) {
      String var3 = I18n.format(Potion.potionTypes[this.v].getName(), new Object[0]);
      if(this.u >= 3 && this.v != Potion.regeneration.getId()) {
         var3 = var3 + " II";
      }

      GuiBeacon.c(this.t, var3, var1, var2);
   }
}
