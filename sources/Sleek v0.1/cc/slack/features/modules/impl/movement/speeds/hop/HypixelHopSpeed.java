package cc.slack.features.modules.impl.movement.speeds.hop;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.client.entity.EntityPlayerSP;

public class HypixelHopSpeed implements ISpeed {
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().onGround) {
         if (MovementUtil.isMoving()) {
            mc.getPlayer().jump();
            MovementUtil.strafe(0.46F);
            mc.getPlayer().motionY = PlayerUtil.getJumpHeight();
         }
      } else {
         EntityPlayerSP var10000 = mc.getPlayer();
         var10000.motionX *= 1.001D;
         var10000 = mc.getPlayer();
         var10000.motionZ *= 1.001D;
      }

   }

   public String toString() {
      return "Hypixel Hop";
   }
}
