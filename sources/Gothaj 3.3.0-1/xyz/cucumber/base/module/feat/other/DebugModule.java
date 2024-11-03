package xyz.cucumber.base.module.feat.other;

import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;

@ModuleInfo(
   category = Category.OTHER,
   description = "Debugs aura",
   name = "Debug",
   priority = ArrayPriority.LOW
)
public class DebugModule extends Mod {
   KillAuraModule ka;

   @Override
   public void onEnable() {
      this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
   }

   @EventListener
   public void onTick(EventTick e) {
      if (this.ka.isEnabled() && this.ka.target != null) {
         Client.INSTANCE.getCommandManager().sendChatMessage("Cps: " + this.ka.randomCPS);
      }
   }
}
