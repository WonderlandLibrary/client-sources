package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

@Module.Info(
   name = "Clock",
   category = Module.Category.HUD
)
public class Clock extends Module {
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(Clock::lambda$new$1).build());
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(Clock::lambda$new$0).build());

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   public int getHeight() {
      return this.fontRenderer.getStringHeight((new SimpleDateFormat("H:mm")).format(new Date()));
   }

   public int getWidth() {
      return this.fontRenderer.getStringWidth((new SimpleDateFormat("H:mm")).format(new Date()));
   }

   public void onRender() {
      int var1 = (Integer)this.x.getValue();
      int var2 = (Integer)this.y.getValue();
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      this.fontRenderer.drawStringWithShadow((new SimpleDateFormat("H:mm")).format(new Date()), (double)var1, (double)var2, -1);
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }
}
