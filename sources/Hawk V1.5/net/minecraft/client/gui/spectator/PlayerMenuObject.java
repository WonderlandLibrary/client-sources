package net.minecraft.client.gui.spectator;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class PlayerMenuObject implements ISpectatorMenuObject {
   private final GameProfile field_178668_a;
   private static final String __OBFID = "CL_00001929";
   private final ResourceLocation field_178667_b;

   public void func_178663_a(float var1, int var2) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178667_b);
      GlStateManager.color(1.0F, 1.0F, 1.0F, (float)var2 / 255.0F);
      Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
   }

   public IChatComponent func_178664_z_() {
      return new ChatComponentText(this.field_178668_a.getName());
   }

   public PlayerMenuObject(GameProfile var1) {
      this.field_178668_a = var1;
      this.field_178667_b = AbstractClientPlayer.getLocationSkin(var1.getName());
      AbstractClientPlayer.getDownloadImageSkin(this.field_178667_b, var1.getName());
   }

   public boolean func_178662_A_() {
      return true;
   }

   public void func_178661_a(SpectatorMenu var1) {
      Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C18PacketSpectate(this.field_178668_a.getId()));
   }
}
