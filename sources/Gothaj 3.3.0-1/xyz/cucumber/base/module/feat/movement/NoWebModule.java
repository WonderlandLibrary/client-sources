package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to move faster in web",
   name = "No Web",
   key = 0
)
public class NoWebModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Intave", "Vanilla"});
   public BooleanSettings shift = new BooleanSettings("k", false);
   public boolean sneaking;

   public NoWebModule() {
      this.addSettings(new ModuleSettings[]{this.mode, this.shift});
   }

   @Override
   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onTick(EventTick e) {
      this.setInfo(this.mode.getMode());
      if (this.mc.thePlayer.isInWeb) {
         if (this.mc.thePlayer.isInWeb && this.shift.isEnabled()) {
            this.mc.gameSettings.keyBindSneak.pressed = true;
            this.sneaking = true;
         }
      } else if (this.sneaking) {
         this.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode());
         this.sneaking = false;
      }

      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var2.equals("intave") && MovementUtils.isMoving() && this.mc.thePlayer.isInWeb) {
               this.mc.gameSettings.keyBindJump.pressed = false;
               if (!this.mc.thePlayer.onGround) {
                  this.mc.timer.timerSpeed = 1.0F;
                  if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                     MovementUtils.strafe(
                        0.65F, (float)MovementUtils.getDirection(RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw)
                     );
                  } else if (this.mc.thePlayer.ticksExisted % 5 == 0) {
                     MovementUtils.strafe(
                        0.65F, (float)MovementUtils.getDirection(RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw)
                     );
                  }
               } else {
                  MovementUtils.strafe(
                     0.35F, (float)MovementUtils.getDirection(RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw)
                  );
                  this.mc.thePlayer.jump();
                  this.mc.thePlayer.jump();
                  this.mc.thePlayer.jump();
               }

               if (!this.mc.thePlayer.isSprinting()) {
                  this.mc.thePlayer.motionX *= 0.75;
                  this.mc.thePlayer.motionZ *= 0.75;
               }
            }
            break;
         case 233102203:
            if (var2.equals("vanilla") && MovementUtils.isMoving() && this.mc.thePlayer.isInWeb) {
               this.mc.thePlayer.isInWeb = false;
            }
      }
   }
}
