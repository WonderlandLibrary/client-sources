package xyz.cucumber.base.module.feat.player;

import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.StringSettings;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Allows you to hide your nick",
   name = "Name Protect",
   priority = ArrayPriority.LOW
)
public class NameProtectModule extends Mod {
   public BooleanSettings cl = new BooleanSettings("Show CL users", false);
   public StringSettings protname = new StringSettings("Name", "&b&lProtected");

   public NameProtectModule() {
      this.addSettings(new ModuleSettings[]{this.cl, this.protname});
   }

   public String getFormatedName() {
      return this.protname.getString().replace("&", "Â§");
   }
}
