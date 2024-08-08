package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Friends;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Comparator;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@Module.Info(
   name = "PvpInfo",
   category = Module.Category.HUD
)
public class PvpInfo extends Module {
   private Setting width = this.register(SettingsManager.integerBuilder("width").withValue((int)100).withVisibility(PvpInfo::lambda$new$2).build());
   private Setting height = this.register(SettingsManager.integerBuilder("height").withValue((int)35).withVisibility(PvpInfo::lambda$new$3).build());
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(PvpInfo::lambda$new$0).build());
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(PvpInfo::lambda$new$1).build());
   private static EntityPlayer currentPlayer = null;
   private static boolean autoUpdateCurrentPlayer = true;

   private static Float lambda$onRender$6(EntityPlayer var0) {
      return mc.field_71439_g.func_70032_d(var0);
   }

   private static boolean lambda$new$3(Integer var0) {
      return false;
   }

   public int getHeight() {
      return (Integer)this.height.getValue();
   }

   public int getWidth() {
      return (Integer)this.width.getValue();
   }

   private static EntityPlayer lambda$onRender$5(Entity var0) {
      return (EntityPlayer)var0;
   }

   private static boolean lambda$new$2(Integer var0) {
      return false;
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   private static boolean lambda$onRender$4(Entity var0) {
      return var0 instanceof EntityPlayer && var0 != mc.field_71439_g && var0.func_70005_c_() != mc.field_71439_g.func_70005_c_() && !Friends.isFriend(var0.func_70005_c_());
   }

   private void drawCharacter(float var1, float var2, int var3, int var4, int var5, EntityPlayer var6) {
      float var7 = var1 - (float)var4;
      float var8 = var2 - (float)var3 * 1.67F - (float)var5;
      GlStateManager.func_179101_C();
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179090_x();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      GuiInventory.func_147046_a((int)var1, (int)var2, var3, var7, var8, var6);
      GlStateManager.func_179091_B();
      GlStateManager.func_179098_w();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
   }

   public void onRender() {
      if (mc.field_71439_g != null) {
         if (autoUpdateCurrentPlayer) {
            currentPlayer = (EntityPlayer)mc.field_71441_e.field_72996_f.stream().filter(PvpInfo::lambda$onRender$4).map(PvpInfo::lambda$onRender$5).min(Comparator.comparing(PvpInfo::lambda$onRender$6)).orElse((Object)null);
         }

         if (currentPlayer != null) {
            GlStateManager.func_179094_E();
            RenderHelper.func_74520_c();
            if (mc.field_71462_r instanceof EditmeHUDEditor) {
               RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)(Integer)this.width.getValue(), (float)(Integer)this.height.getValue(), (new Color(153, 153, 153, 102)).getRGB());
            }

            try {
               this.drawCharacter((float)((Integer)this.x.getValue() + 10), (float)((Integer)this.y.getValue() + 25), 10, (Integer)this.x.getValue() + 120, (Integer)this.y.getValue() + 20, currentPlayer);
            } catch (Exception var7) {
            }

            this.fontRenderer.drawStringWithShadow(currentPlayer.func_70005_c_(), (double)((Integer)this.x.getValue() + 20), (double)((Integer)this.y.getValue() + 4), 16777215);
            DecimalFormat var1 = new DecimalFormat("#.##");
            double var2 = (double)mc.field_71439_g.func_70032_d(currentPlayer);
            String var4 = String.valueOf((new StringBuilder()).append("Distance: ").append(var1.format(var2)));
            if (!var4.contains(".")) {
               var4 = String.valueOf((new StringBuilder()).append(var4).append(".00"));
            } else {
               String[] var5 = var4.split("\\.");
               if (var5 != null && var5[1] != null && var5[1].length() != 2) {
                  var2 += 0.0D;
               }
            }

            this.fontRenderer.drawStringWithShadow(var4, (double)((Integer)this.x.getValue() + 20), (double)((Integer)this.y.getValue() + 8 + this.fontRenderer.getStringHeight(currentPlayer.func_70005_c_())), 16777215);
            float var8 = currentPlayer.func_110143_aJ() + currentPlayer.func_110139_bj();
            String var6 = String.valueOf((new StringBuilder()).append("Health: ").append(var1.format((double)var8)));
            this.fontRenderer.drawStringWithShadow(var6, (double)((Integer)this.x.getValue() + 20), (double)((Integer)this.y.getValue() + 12 + this.fontRenderer.getStringHeight(currentPlayer.func_70005_c_()) + this.fontRenderer.getStringHeight(var4)), 16777215);
            RenderHelper.func_74518_a();
            GlStateManager.func_179121_F();
         }
      }
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }
}
