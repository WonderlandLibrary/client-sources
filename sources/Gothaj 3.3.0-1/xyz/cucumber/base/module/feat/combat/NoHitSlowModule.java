package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventHit;
import xyz.cucumber.base.events.ext.EventKnockBack;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you to remove hit slowdown",
   name = "No Hit Slow",
   key = 0
)
public class NoHitSlowModule extends Mod {
   public BooleanSettings onlyHurtTime = new BooleanSettings("Only Hurt Time", true);

   public NoHitSlowModule() {
      this.addSettings(new ModuleSettings[]{this.onlyHurtTime});
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onHit(EventHit e) {
      KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      VelocityModule velocity = (VelocityModule)Client.INSTANCE.getModuleManager().getModule(VelocityModule.class);
      if (this.mc.thePlayer.hurtTime <= 0
         || (velocity.mode.getMode().equalsIgnoreCase("Vanilla") || velocity.mode.getMode().equalsIgnoreCase("Vulcan"))
            && velocity.horizontal.getValue() == 0.0
            && velocity.vertical.getValue() == 0.0
            && velocity.isEnabled()) {
         e.setCancelled(true);
      }

      if (this.onlyHurtTime.isEnabled() && this.mc.thePlayer.hurtTime == 0) {
         e.setCancelled(false);
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onKnockBack(EventKnockBack e) {
      KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      VelocityModule velocity = (VelocityModule)Client.INSTANCE.getModuleManager().getModule(VelocityModule.class);
      if (this.mc.thePlayer.hurtTime <= 0
         || (velocity.mode.getMode().equalsIgnoreCase("Vanilla") || velocity.mode.getMode().equalsIgnoreCase("Vulcan"))
            && velocity.horizontal.getValue() == 0.0
            && velocity.vertical.getValue() == 0.0
            && velocity.isEnabled()) {
         e.setCancelled(true);
      }

      if (this.onlyHurtTime.isEnabled() && this.mc.thePlayer.hurtTime == 0) {
         e.setCancelled(false);
      }
   }
}
