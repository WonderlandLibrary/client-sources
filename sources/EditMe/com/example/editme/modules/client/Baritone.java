package com.example.editme.modules.client;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;

@Module.Info(
   name = "Baritone",
   description = "Visual Baritone config",
   category = Module.Category.CLIENT
)
public class Baritone extends Module {
   private Setting allowSprint = this.register(SettingsManager.b("Allow Sprint", true));
   private Setting allowFreeLook = this.register(SettingsManager.b("Free Look", true));
   private Setting allowPlace = this.register(SettingsManager.b("Allow Place", true));
   private Setting allowBreak = this.register(SettingsManager.b("Allow Break", true));
   private Setting allowInventory = this.register(SettingsManager.b("Allow Inventory", false));
}
