package net.minecraft.client.gui.spectator;

import com.mojang.authlib.GameProfile;
import net.aSQ;
import net.asJ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class PlayerMenuObject implements ISpectatorMenuObject {
   private final GameProfile profile;
   private final ResourceLocation resourceLocation;

   public PlayerMenuObject(GameProfile var1) {
      this.profile = var1;
      this.resourceLocation = asJ.a(var1.getName());
      asJ.a(this.resourceLocation, var1.getName());
   }

   public void func_178661_a(SpectatorMenu var1) {
      Minecraft.getInstance().getNetHandler().b(new C18PacketSpectate(this.profile.getId()));
   }

   public IChatComponent getSpectatorName() {
      return new ChatComponentText(this.profile.getName());
   }

   public void func_178663_a(float var1, int var2) {
      Minecraft.getInstance().getTextureManager().bindTexture(this.resourceLocation);
      GlStateManager.color(1.0F, 1.0F, 1.0F, (float)var2 / 255.0F);
      aSQ.a(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      aSQ.a(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
   }

   public boolean func_178662_A_() {
      return true;
   }
}
