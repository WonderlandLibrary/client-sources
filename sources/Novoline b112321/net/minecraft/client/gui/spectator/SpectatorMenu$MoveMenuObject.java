package net.minecraft.client.gui.spectator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

class SpectatorMenu$MoveMenuObject implements ISpectatorMenuObject {
   private final int field_178666_a;
   private final boolean field_178665_b;

   public SpectatorMenu$MoveMenuObject(int var1, boolean var2) {
      this.field_178666_a = var1;
      this.field_178665_b = var2;
   }

   public void func_178661_a(SpectatorMenu var1) {
      SpectatorMenu.access$102(var1, this.field_178666_a);
   }

   public IChatComponent getSpectatorName() {
      return this.field_178666_a < 0?new ChatComponentText("Previous Page"):new ChatComponentText("Next Page");
   }

   public void func_178663_a(float var1, int var2) {
      Minecraft.getInstance().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
      if(this.field_178666_a < 0) {
         Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
      } else {
         Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
      }

   }

   public boolean func_178662_A_() {
      return this.field_178665_b;
   }
}
