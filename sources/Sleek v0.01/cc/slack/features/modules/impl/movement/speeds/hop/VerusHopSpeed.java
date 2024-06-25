package cc.slack.features.modules.impl.movement.speeds.hop;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;

public class VerusHopSpeed implements ISpeed {
   double moveSpeed;
   int airTicks;

   public void onEnable() {
      this.moveSpeed = 0.0D;
      this.airTicks = 0;
   }

   public void onMove(MoveEvent event) {
      if (mc.getPlayer().onGround) {
         if (MovementUtil.isMoving()) {
            event.setY(0.41999998688697815D);
         }

         this.moveSpeed = 0.6800000071525574D;
         this.airTicks = 0;
      } else {
         if (this.airTicks == 0) {
            this.moveSpeed *= 0.5934960376506356D;
         }

         ++this.airTicks;
      }

      MovementUtil.setSpeed(event, this.moveSpeed);
      this.moveSpeed *= 0.9800000190734863D;
   }

   public String toString() {
      return "Verus Hop";
   }
}
