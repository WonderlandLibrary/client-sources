package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.aSQ;
import net.asJ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

class TeleportToTeam$TeamSelectionObject implements ISpectatorMenuObject {
   private final ScorePlayerTeam field_178676_b;
   private final ResourceLocation field_178677_c;
   private final List field_178675_d;

   public TeleportToTeam$TeamSelectionObject(ScorePlayerTeam var1) {
      this.field_178676_b = var1;
      this.field_178675_d = Lists.newArrayList();

      for(String var3 : var1.getMembershipCollection()) {
         NetworkPlayerInfo var4 = Minecraft.getInstance().getNetHandler().getPlayerInfo(var3);
         this.field_178675_d.add(var4);
      }

      if(!this.field_178675_d.isEmpty()) {
         String var5 = ((NetworkPlayerInfo)this.field_178675_d.get((new Random()).nextInt(this.field_178675_d.size()))).getGameProfile().getName();
         this.field_178677_c = asJ.a(var5);
         asJ.a(this.field_178677_c, var5);
      } else {
         this.field_178677_c = DefaultPlayerSkin.getDefaultSkinLegacy();
      }

   }

   public void func_178661_a(SpectatorMenu var1) {
      var1.func_178647_a(new TeleportToPlayer(this.field_178675_d));
   }

   public IChatComponent getSpectatorName() {
      return new ChatComponentText(this.field_178676_b.getTeamName());
   }

   public void func_178663_a(float var1, int var2) {
      int var3 = -1;
      String var4 = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
      if(var4.length() >= 2) {
         var3 = Minecraft.getInstance().fontRendererObj.getColorCode(var4.charAt(1));
      }

      float var5 = (float)(var3 >> 16 & 255) / 255.0F;
      float var6 = (float)(var3 >> 8 & 255) / 255.0F;
      float var7 = (float)(var3 & 255) / 255.0F;
      Gui.drawRect(1, 1, 15, 15, MathHelper.func_180183_b(var5 * var1, var6 * var1, var7 * var1) | var2 << 24);
      Minecraft.getInstance().getTextureManager().bindTexture(this.field_178677_c);
      GlStateManager.color(var1, var1, var1, (float)var2 / 255.0F);
      aSQ.a(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      aSQ.a(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
   }

   public boolean func_178662_A_() {
      return !this.field_178675_d.isEmpty();
   }
}
