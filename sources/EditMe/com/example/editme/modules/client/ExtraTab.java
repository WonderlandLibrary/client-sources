package com.example.editme.modules.client;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;

@Module.Info(
   name = "BetterTab",
   description = "Expands the player tab menu",
   category = Module.Category.CLIENT
)
public class ExtraTab extends Module {
   public Setting tabSize = this.register(SettingsManager.integerBuilder("Players").withMinimum(1).withValue((int)80).build());
   private Setting showFriends = this.register(SettingsManager.b("Show Friends", true));
   public static ExtraTab INSTANCE;

   protected void onEnable() {
      super.onEnable();
      if ((Boolean)this.showFriends.getValue()) {
         ModuleManager.enableModule("TabFriends");
      }

   }

   public ExtraTab() {
      INSTANCE = this;
   }

   protected void onDisable() {
      super.onDisable();
      ModuleManager.disableModule("TabFriends");
   }
}
