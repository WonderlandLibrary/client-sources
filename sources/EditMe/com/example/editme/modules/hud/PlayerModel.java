package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;

@Module.Info(
   name = "PlayerModel",
   category = Module.Category.HUD
)
public class PlayerModel extends Module {
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(PlayerModel::lambda$new$1).build());
   private Setting width = this.register(SettingsManager.integerBuilder("width").withValue((int)30).withVisibility(PlayerModel::lambda$new$2).build());
   private Setting height = this.register(SettingsManager.integerBuilder("height").withValue((int)30).withVisibility(PlayerModel::lambda$new$3).build());
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(PlayerModel::lambda$new$0).build());

   public int getWidth() {
      return (Integer)this.width.getValue();
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   private static boolean lambda$new$2(Integer var0) {
      return false;
   }

   public void onRender() {
      if (mc.field_71439_g != null) {
         GlStateManager.func_179094_E();
         RenderHelper.func_74520_c();
         if (mc.field_71462_r instanceof EditmeHUDEditor) {
            RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)(Integer)this.width.getValue(), (float)(Integer)this.height.getValue(), (new Color(153, 153, 153, 102)).getRGB());
         }

         try {
            this.drawCharacter((float)((Integer)this.x.getValue() + 10), (float)((Integer)this.y.getValue() + 25), 10, (Integer)this.x.getValue() + 120, (Integer)this.y.getValue() + 20, mc.field_71439_g);
         } catch (Exception var2) {
         }

         RenderHelper.func_74518_a();
         GlStateManager.func_179121_F();
      }
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
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

   public int getHeight() {
      return (Integer)this.height.getValue();
   }

   private static boolean lambda$new$3(Integer var0) {
      return false;
   }
}
