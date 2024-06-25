package cc.slack.features.modules.impl.movement.speeds.hop;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;

public class VulcanHopSpeed implements ISpeed {
   boolean modifiedTimer;

   public void onUpdate(UpdateEvent event) {
      if (this.modifiedTimer) {
         mc.getTimer().timerSpeed = 1.0F;
         this.modifiedTimer = false;
      }

      if (MovementUtil.getSpeed() < 0.2150000035762787D && !mc.getPlayer().onGround) {
         MovementUtil.strafe(0.215F);
      }

      if (mc.getPlayer().onGround && MovementUtil.isMoving()) {
         mc.getPlayer().jump();
         mc.getTimer().timerSpeed = 1.25F;
         this.modifiedTimer = true;
         MovementUtil.minLimitStrafe(0.4849F);
      } else if (!MovementUtil.isMoving()) {
         mc.getTimer().timerSpeed = 1.0F;
         MovementUtil.resetMotion();
      }

   }

   public String toString() {
      return "Vulcan Hop";
   }
}
