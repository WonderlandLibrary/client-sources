package cc.slack.features.modules.impl.movement.speeds.lowhops;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;

public class VulcanLowSpeed implements ISpeed {
   double launchY = 0.0D;

   public void onEnable() {
      this.launchY = mc.getPlayer().motionY;
   }

   public void onUpdate(UpdateEvent event) {
      mc.getPlayer().jumpMovementFactor = 0.0245F;
      if (mc.getPlayer().onGround && MovementUtil.isMoving()) {
         mc.getPlayer().jump();
         MovementUtil.strafe();
         if (MovementUtil.getSpeed() < 0.5D) {
            MovementUtil.strafe(0.484F);
         }

         this.launchY = mc.getPlayer().posY;
      } else if (mc.getPlayer().offGroundTicks == 4) {
         mc.getPlayer().motionY = -0.27D;
      }

      if (MovementUtil.getSpeed() < 0.2150000035762787D && !mc.getPlayer().onGround) {
         MovementUtil.strafe(0.215F);
      }

   }

   public String toString() {
      return "Vulcan Low";
   }
}
