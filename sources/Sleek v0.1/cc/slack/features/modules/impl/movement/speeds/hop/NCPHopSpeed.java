package cc.slack.features.modules.impl.movement.speeds.hop;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;

public class NCPHopSpeed implements ISpeed {
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().onGround) {
         if (MovementUtil.isMoving()) {
            mc.getPlayer().jump();
            double baseSpeed = 0.484D;
            if (mc.getPlayer().isPotionActive(Potion.moveSpeed)) {
               double amplifier = (double)mc.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
               baseSpeed *= 1.0D + 0.13D * (amplifier + 1.0D);
            }

            MovementUtil.minLimitStrafe((float)baseSpeed);
         }
      } else {
         EntityPlayerSP var10000 = mc.getPlayer();
         var10000.motionX *= 1.001D;
         var10000 = mc.getPlayer();
         var10000.motionZ *= 1.001D;
         if (mc.getPlayer().offGroundTicks == 5) {
            mc.getPlayer().motionY = -0.07840000152D;
         }

         MovementUtil.strafe();
      }

   }

   public String toString() {
      return "NCP Hop";
   }
}
