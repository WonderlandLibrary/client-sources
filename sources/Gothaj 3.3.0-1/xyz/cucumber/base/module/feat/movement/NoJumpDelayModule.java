package xyz.cucumber.base.module.feat.movement;

import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to jump without delay",
   name = "No Jump Delay",
   key = 0
)
public class NoJumpDelayModule extends Mod {
   @EventListener
   public void onTick(EventTick e) {
      this.mc.thePlayer.jumpTicks = 0;
   }
}
