package com.example.editme.modules;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;

@Module.Info(
   name = "hudGUI",
   description = "Opens the HUD editor GUI",
   category = Module.Category.HIDDEN
)
public class HudGUI extends Module {
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)2).withVisibility(HudGUI::lambda$new$1).build());
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)482).withVisibility(HudGUI::lambda$new$0).build());
   private EditmeHUDEditor editmeHudEditor;
   private Setting e = this.register(SettingsManager.booleanBuilder("e").withValue(false).withVisibility(HudGUI::lambda$new$2).build());

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   private static boolean lambda$new$2(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   public void onEnable() {
      if (this.editmeHudEditor == null) {
         this.editmeHudEditor = new EditmeHUDEditor();
         this.editmeHudEditor.initialize();
      }

      mc.func_147108_a(this.editmeHudEditor);
      this.toggle();
   }
}
