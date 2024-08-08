package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Friends;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

@Module.Info(
   name = "Text Radar",
   category = Module.Category.HUD
)
public class TextRadar extends Module {
   String currentString;
   Color currentColor;
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(TextRadar::lambda$new$0).build());
   private int height;
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(TextRadar::lambda$new$1).build());

   public int getWidth() {
      return this.fontRenderer.getStringWidth("AAAAAAAAAAAAAAAAAAAAAAAAA 20.00 STRN");
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   public void onRender() {
      int var1 = (Integer)this.x.getValue();
      int var2 = (Integer)this.y.getValue();
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      List var3 = mc.field_71441_e.field_73010_i;
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         Entity var5 = (Entity)var4.next();
         if (!var5.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
            this.currentString = "";
            EntityPlayer var6 = (EntityPlayer)var5;
            this.appendToString(var6.func_70005_c_());
            int var7 = (int)(var6.func_110143_aJ() + var6.func_110139_bj());
            this.appendToString(String.valueOf((new StringBuilder()).append(var7).append("")));
            PotionEffect var8 = var6.func_70660_b(MobEffects.field_76420_g);
            if (var8 != null && var6.func_70644_a(MobEffects.field_76420_g)) {
               this.appendToString("STRN");
            }

            PotionEffect var9 = var6.func_70660_b(MobEffects.field_76437_t);
            if (var9 != null && var6.func_70644_a(MobEffects.field_76437_t)) {
               this.appendToString("WEAK");
            }

            this.currentColor = new Color(200, 75, 75);
            if (Friends.isFriend(var6.func_70005_c_().toLowerCase())) {
               this.currentColor = new Color(75, 150, 75);
            }

            this.fontRenderer.drawStringWithShadow(this.currentString, (double)var1, (double)var2, this.currentColor.getRGB());
            var2 += this.fontRenderer.getStringHeight(var6.func_70005_c_()) + 5;
         }
      }

      this.height = var2 - (Integer)this.y.getValue();
   }

   private void appendToString(String var1) {
      this.currentString = String.valueOf((new StringBuilder()).append(this.currentString).append(" ").append(var1));
   }

   public int getHeight() {
      return Math.max(this.height, 20);
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }
}
