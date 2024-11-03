package xyz.cucumber.base.module.feat.combat;

import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.ext.EventClick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you to hit without delay",
   name = "Delay Fix"
)
public class DelayFixModule extends Mod {
   @EventListener(EventPriority.LOW)
   public void onTick(EventClick e) {
      this.mc.leftClickCounter = 0;
   }
}
