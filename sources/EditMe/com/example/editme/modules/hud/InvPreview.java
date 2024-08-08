package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "InvPreview",
   category = Module.Category.HUD
)
public class InvPreview extends Module {
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(InvPreview::lambda$new$0).build());
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(InvPreview::lambda$new$1).build());

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   public int getWidth() {
      return 144;
   }

   public void onRender() {
      GlStateManager.func_179094_E();
      RenderHelper.func_74520_c();
      GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      for(int var1 = 0; var1 < 27; ++var1) {
         ItemStack var2 = (ItemStack)mc.field_71439_g.field_71071_by.field_70462_a.get(var1 + 9);
         int var3 = (Integer)this.x.getValue() + var1 % 9 * 16;
         int var4 = (Integer)this.y.getValue() + var1 / 9 * 16;
         mc.func_175599_af().func_180450_b(var2, var3, var4);
         mc.func_175599_af().func_180453_a(mc.field_71466_p, var2, var3, var4, (String)null);
      }

      RenderHelper.func_74518_a();
      mc.func_175599_af().field_77023_b = 0.0F;
      GlStateManager.func_179121_F();
   }

   public int getHeight() {
      return 48;
   }
}
