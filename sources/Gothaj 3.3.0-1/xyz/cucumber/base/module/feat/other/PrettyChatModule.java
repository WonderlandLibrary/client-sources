package xyz.cucumber.base.module.feat.other;

import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.OTHER,
   description = "Shows you a cool chat",
   name = "Pretty Chat",
   priority = ArrayPriority.LOW
)
public class PrettyChatModule extends Mod {
}
