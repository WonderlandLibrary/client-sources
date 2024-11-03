package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import net.minecraft.util.MathHelper;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.ext.EventMoveForward;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.feat.player.SmoothRotationModule;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to sprint always",
   name = "Sprint",
   key = 0
)
public class SprintModule extends Mod {
   @EventListener(EventPriority.LOWEST)
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveForward(EventMoveForward e) {
      if (!e.isReset()) {
         if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled() || !RotationUtils.customRots) {
            this.mc.gameSettings.keyBindSprint.pressed = true;
            if (this.mc.thePlayer.moveForward != 0.0F) {
               if (!this.mc.thePlayer.isCollidedHorizontally
                  && !this.mc.thePlayer.isSneaking()
                  && this.mc.gameSettings.keyBindForward.pressed
                  && !this.mc.thePlayer.isSprinting()
                  && this.mc.thePlayer.isUsingItem()
                  && (Client.INSTANCE.getModuleManager().getModule(NoSlowModule.class).isEnabled() || !this.mc.gameSettings.keyBindUseItem.pressed)) {
                  this.mc.thePlayer.setSprinting(true);
               }

               KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule("Kill Aura");
               if (RotationUtils.customRots
                  && ka.MoveFix.getMode().equalsIgnoreCase("Silent")
                  && ka.isEnabled()
                  && ka.allowedToWork
                  && (double)Math.abs(
                        MathHelper.wrapAngleTo180_float((float)Math.toDegrees(MovementUtils.getDirection(this.mc.thePlayer.rotationYaw)))
                           - MathHelper.wrapAngleTo180_float(RotationUtils.serverYaw)
                     )
                     > 67.5) {
                  this.mc.gameSettings.keyBindSprint.pressed = false;
                  this.mc.thePlayer.setSprinting(false);
               }

               ScaffoldModule scaffold = (ScaffoldModule)Client.INSTANCE.getModuleManager().getModule("Scaffold");
               SmoothRotationModule smoothRotation = (SmoothRotationModule)Client.INSTANCE.getModuleManager().getModule(SmoothRotationModule.class);
               if (RotationUtils.customRots
                  && smoothRotation.isEnabled()
                  && !scaffold.isEnabled()
                  && smoothRotation.wasScaffold
                  && (double)Math.abs(
                        MathHelper.wrapAngleTo180_float((float)Math.toDegrees(MovementUtils.getDirection(this.mc.thePlayer.rotationYaw)))
                           - MathHelper.wrapAngleTo180_float(RotationUtils.serverYaw)
                     )
                     > 67.5) {
                  this.mc.gameSettings.keyBindSprint.pressed = false;
                  this.mc.thePlayer.setSprinting(false);
               }
            }
         }
      }
   }
}
