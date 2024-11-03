package xyz.cucumber.base.module.feat.player;

import god.buddy.aot.BCompiler;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Automatically put water when you are on fire",
   name = "Anti Fire"
)
public class AntiFireModule extends Mod {
   public Timer timer = new Timer();
   public boolean canWork;
   public boolean done;

   @EventListener(EventPriority.HIGHEST)
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion e) {
      if (this.canWork && e.getType() == EventType.PRE) {
         e.setYaw(RotationUtils.serverYaw);
         e.setPitch(90.0F);
         this.done = false;
      }
   }

   @EventListener(EventPriority.HIGHEST)
   public void onLook(EventRenderRotation e) {
      if (this.canWork) {
         e.setYaw(RotationUtils.serverYaw);
         e.setPitch(90.0F);
      }
   }

   @EventListener(EventPriority.HIGHEST)
   public void onLook(EventLook e) {
      if (this.mc.thePlayer.isBurning()) {
         int slot = InventoryUtils.getBucketSlot();
         if (slot == -1) {
            this.cancel();
            return;
         }

         this.canWork = true;
         this.mc.thePlayer.inventory.currentItem = slot;
         this.timer.reset();
      } else {
         if (!this.mc.thePlayer.isInWater()) {
            this.cancel();
            return;
         }

         int slot = InventoryUtils.getEmptyBucketSlot();
         if (slot == -1) {
            this.cancel();
            return;
         }

         if (this.timer.hasTimeElapsed(100.0, false)) {
            this.cancel();
            return;
         }

         this.canWork = true;
         this.mc.thePlayer.inventory.currentItem = slot;
      }

      if (this.canWork) {
         e.setPitch(90.0F);
         RotationUtils.customRots = true;
         RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
         RotationUtils.serverPitch = 90.0F;
         if (!this.done) {
            this.mc.rightClickMouse();
         }

         this.done = true;
      }
   }

   public void cancel() {
      if (this.canWork) {
         this.done = false;
         this.canWork = false;
         RotationUtils.customRots = false;
         RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
         RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
      }
   }
}
