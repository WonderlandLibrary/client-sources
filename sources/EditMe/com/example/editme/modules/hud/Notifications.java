package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Notification;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

@Module.Info(
   name = "Notifications",
   category = Module.Category.HUD
)
public class Notifications extends Module {
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(Notifications::lambda$new$0).build());
   private int currentPos = 0;
   private static List queue = new ArrayList();
   private int currentShowTime = 0;
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(Notifications::lambda$new$1).build());

   public int getWidth() {
      return 100;
   }

   public int getHeight() {
      return this.fontRenderer.getStringHeight("ALLAH") + 10;
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   public static void addNotif(Notification var0) {
      queue.add(var0);
   }

   public void onRender() {
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      if (queue != null) {
         if (queue.size() != 0) {
            if (this.currentShowTime > 40 && this.currentPos < queue.size() - 1) {
               ++this.currentPos;
               this.currentShowTime = 0;
            }

            if (this.currentShowTime <= 40) {
               ++this.currentShowTime;
               int var1 = this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(((Notification)queue.get(this.currentPos)).header).append("   ").append(((Notification)queue.get(this.currentPos)).content)));
               RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)var1, (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
               this.fontRenderer.drawStringWithShadow(((Notification)queue.get(this.currentPos)).header, (double)((Integer)this.x.getValue() + 95 - var1), (double)((Integer)this.y.getValue() + 5), (new Color(0, 102, 204)).getRGB());
               String var2 = String.valueOf((new StringBuilder()).append("   ").append(((Notification)queue.get(this.currentPos)).content));
               this.fontRenderer.drawStringWithShadow(var2, (double)((Integer)this.x.getValue() + 95 - this.fontRenderer.getStringWidth(var2)), (double)((Integer)this.y.getValue() + 5), (new Color(255, 255, 255)).getRGB());
            }

         }
      }
   }
}
