package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.Iterator;

@Module.Info(
   name = "ActiveModules",
   category = Module.Category.HUD
)
public class ActiveModules extends Module {
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(ActiveModules::lambda$new$0).build());
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(ActiveModules::lambda$new$1).build());
   int color;

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   public int getHeight() {
      int var1 = 10;
      Iterator var2 = ModuleManager.getModules().iterator();

      while(true) {
         Module var3;
         do {
            if (!var2.hasNext()) {
               return var1;
            }

            var3 = (Module)var2.next();
         } while(!var3.isEnabled());

         Iterator var4 = var3.settingList.iterator();

         while(var4.hasNext()) {
            Setting var5 = (Setting)var4.next();
            if (var5.getName().equals("Visible") && !var3.getCategory().isHidden() && (Boolean)var5.getValue()) {
               var1 += this.fontRenderer.getStringHeight(var3.getName()) + 5;
            }
         }
      }
   }

   public int getWidth() {
      int var1 = 10;
      Iterator var2 = ModuleManager.getModules().iterator();

      while(true) {
         Module var3;
         do {
            do {
               if (!var2.hasNext()) {
                  return var1;
               }

               var3 = (Module)var2.next();
            } while(!var3.isEnabled());
         } while(var3.getCategory().isHidden());

         Iterator var4 = var3.settingList.iterator();

         while(var4.hasNext()) {
            Setting var5 = (Setting)var4.next();
            if (var5.getName().equals("Visible") && (Boolean)var5.getValue()) {
               var1 = Math.max(this.fontRenderer.getStringWidth(var3.getName()), var1);
            }
         }
      }
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

      Iterator var3 = ModuleManager.getModules().iterator();

      while(true) {
         Module var4;
         do {
            do {
               if (!var3.hasNext()) {
                  return;
               }

               var4 = (Module)var3.next();
            } while(!var4.isEnabled());
         } while(var4.getCategory().isHidden());

         Iterator var5 = var4.settingList.iterator();

         while(var5.hasNext()) {
            Setting var6 = (Setting)var5.next();
            if (var6.getName().equals("Visible") && (Boolean)var6.getValue()) {
               this.fontRenderer.drawStringWithShadow(var4.getName(), (double)(var1 + this.getWidth() - this.fontRenderer.getStringWidth(var4.getName())), (double)var2, this.color);
               var2 += this.fontRenderer.getStringHeight(var4.getName()) + 5;
            }
         }
      }
   }

   public ActiveModules() {
      this.color = Color.WHITE.getRGB();
   }
}
