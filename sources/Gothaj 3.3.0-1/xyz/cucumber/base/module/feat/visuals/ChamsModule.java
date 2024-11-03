package xyz.cucumber.base.module.feat.visuals;

import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Displays entities through walls",
   name = "Chams"
)
public class ChamsModule extends Mod {
   public ColorSettings color = new ColorSettings("Color", "Static", -1, -1, 30);

   public ChamsModule() {
      this.addSettings(new ModuleSettings[]{this.color});
   }
}
