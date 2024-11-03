package xyz.cucumber.base.module.feat.visuals;

import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Automatically sets your gamma to max",
   name = "Full Bright",
   priority = ArrayPriority.LOW
)
public class FullBrightModule extends Mod {
   @Override
   public void onEnable() {
      this.mc.gameSettings.gammaSetting = 1000.0F;
   }

   @Override
   public void onDisable() {
      this.mc.gameSettings.gammaSetting = 25.0F;
   }
}
