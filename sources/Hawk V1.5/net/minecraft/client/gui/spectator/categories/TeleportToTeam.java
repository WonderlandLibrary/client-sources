package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TeleportToTeam implements ISpectatorMenuObject, ISpectatorMenuView {
   private static final String __OBFID = "CL_00001920";
   private final List field_178672_a = Lists.newArrayList();

   public IChatComponent func_178670_b() {
      return new ChatComponentText("Select a team to teleport to");
   }

   public boolean func_178662_A_() {
      Iterator var1 = this.field_178672_a.iterator();

      while(var1.hasNext()) {
         ISpectatorMenuObject var2 = (ISpectatorMenuObject)var1.next();
         if (var2.func_178662_A_()) {
            return true;
         }
      }

      return false;
   }

   public TeleportToTeam() {
      Minecraft var1 = Minecraft.getMinecraft();
      Iterator var2 = var1.theWorld.getScoreboard().getTeams().iterator();

      while(var2.hasNext()) {
         ScorePlayerTeam var3 = (ScorePlayerTeam)var2.next();
         this.field_178672_a.add(new TeleportToTeam.TeamSelectionObject(this, var3));
      }

   }

   public void func_178661_a(SpectatorMenu var1) {
      var1.func_178647_a(this);
   }

   public List func_178669_a() {
      return this.field_178672_a;
   }

   public void func_178663_a(float var1, int var2) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
      Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   public IChatComponent func_178664_z_() {
      return new ChatComponentText("Teleport to team member");
   }

   class TeamSelectionObject implements ISpectatorMenuObject {
      final TeleportToTeam this$0;
      private final ScorePlayerTeam field_178676_b;
      private final ResourceLocation field_178677_c;
      private static final String __OBFID = "CL_00001919";
      private final List field_178675_d;

      public boolean func_178662_A_() {
         return !this.field_178675_d.isEmpty();
      }

      public TeamSelectionObject(TeleportToTeam var1, ScorePlayerTeam var2) {
         this.this$0 = var1;
         this.field_178676_b = var2;
         this.field_178675_d = Lists.newArrayList();
         Iterator var3 = var2.getMembershipCollection().iterator();

         String var4;
         while(var3.hasNext()) {
            var4 = (String)var3.next();
            NetworkPlayerInfo var5 = Minecraft.getMinecraft().getNetHandler().func_175104_a(var4);
            if (var5 != null) {
               this.field_178675_d.add(var5);
            }
         }

         if (!this.field_178675_d.isEmpty()) {
            var4 = ((NetworkPlayerInfo)this.field_178675_d.get((new Random()).nextInt(this.field_178675_d.size()))).func_178845_a().getName();
            this.field_178677_c = AbstractClientPlayer.getLocationSkin(var4);
            AbstractClientPlayer.getDownloadImageSkin(this.field_178677_c, var4);
         } else {
            this.field_178677_c = DefaultPlayerSkin.func_177335_a();
         }

      }

      public void func_178661_a(SpectatorMenu var1) {
         var1.func_178647_a(new TeleportToPlayer(this.field_178675_d));
      }

      public void func_178663_a(float var1, int var2) {
         int var3 = -1;
         String var4 = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
         if (var4.length() >= 2) {
            var3 = Minecraft.getMinecraft().fontRendererObj.func_175064_b(var4.charAt(1));
         }

         if (var3 >= 0) {
            float var5 = (float)(var3 >> 16 & 255) / 255.0F;
            float var6 = (float)(var3 >> 8 & 255) / 255.0F;
            float var7 = (float)(var3 & 255) / 255.0F;
            Gui.drawRect(1.0D, 1.0D, 15.0D, 15.0D, MathHelper.func_180183_b(var5 * var1, var6 * var1, var7 * var1) | var2 << 24);
         }

         Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
         GlStateManager.color(var1, var1, var1, (float)var2 / 255.0F);
         Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
         Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      }

      public IChatComponent func_178664_z_() {
         return new ChatComponentText(this.field_178676_b.func_96669_c());
      }
   }
}
