package xyz.cucumber.base.module.feat.visuals;

import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Changes your visualization of player",
   name = "Custom Model"
)
public class CustomModelModule extends Mod {
   private ModeSettings mode = new ModeSettings("Mode", new String[]{"Mini", "Amongus", "Fox"});
}
