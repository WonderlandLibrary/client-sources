package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.InfoCalculator;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;

@Module.Info(
   name = "CounterInfo",
   category = Module.Category.HUD
)
public class CounterInfo extends Module {
   int color;
   String currentString;
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(CounterInfo::lambda$new$0).build());
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(CounterInfo::lambda$new$1).build());

   public int getWidth() {
      int var1 = 0;

      try {
         var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("BPS: ").append(InfoCalculator.speed(false, mc, 2)))), var1);
         var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("TPS: ").append(InfoCalculator.tps()))), var1);
         var1 = Math.max(this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("Ping: ").append(InfoCalculator.ping(mc)))), var1);
      } catch (Exception var3) {
      }

      return var1;
   }

   public CounterInfo() {
      this.color = Color.WHITE.getRGB();
   }

   public int getHeight() {
      return this.fontRenderer.getStringHeight("A") * 3 + 10;
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   public void onRender() {
      int var1 = (Integer)this.x.getValue();
      int var2 = (Integer)this.y.getValue();
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("TPS: ").append(InfoCalculator.tps())), (double)var1, (double)var2, this.color);
      var2 += this.fontRenderer.getStringHeight("A") + 5;
      this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("BPS: ").append(InfoCalculator.speed(false, mc, 2))), (double)var1, (double)var2, this.color);
      var2 += this.fontRenderer.getStringHeight("A") + 5;
      this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("Ping: ").append(InfoCalculator.ping(mc))), (double)var1, (double)var2, this.color);
   }
}
