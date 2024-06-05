package net.minecraft.client.gui.spectator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenu$1;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

class SpectatorMenu$EndSpectatorObject implements ISpectatorMenuObject {
   private SpectatorMenu$EndSpectatorObject() {
   }

   public void func_178661_a(SpectatorMenu var1) {
      var1.func_178641_d();
   }

   public IChatComponent getSpectatorName() {
      return new ChatComponentText("Close menu");
   }

   public void func_178663_a(float var1, int var2) {
      Minecraft.getInstance().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
      Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   public boolean func_178662_A_() {
      return true;
   }

   SpectatorMenu$EndSpectatorObject(SpectatorMenu$1 var1) {
      this();
   }
}
