package xyz.cucumber.base.module.feat.movement;

import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Increase step height for blocks",
   name = "Step",
   key = 0
)
public class StepModule extends Mod {
   @EventListener
   public void onMotion(EventMotion e) {
      if (this.mc.thePlayer.ridingEntity != null) {
         if (e.getType() == EventType.PRE) {
            if (!this.mc.thePlayer.onGround) {
               this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posX);
               this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 2.0, this.mc.thePlayer.posX);
               double y = this.mc.thePlayer.posY - 2.0;
               this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, 0.0, this.mc.thePlayer.posX);
               this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, y, this.mc.thePlayer.posX);
               this.mc.timer.timerSpeed = 1.5F;
            }
         } else if (!this.mc.thePlayer.onGround) {
            this.mc.timer.timerSpeed = 1.0F;
         }
      }
   }
}
