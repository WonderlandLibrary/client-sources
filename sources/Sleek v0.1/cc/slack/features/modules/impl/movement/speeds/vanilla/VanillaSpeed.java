package cc.slack.features.modules.impl.movement.speeds.vanilla;

import cc.slack.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.Speed;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;

public class VanillaSpeed implements ISpeed {
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().onGround && MovementUtil.isMoving()) {
         mc.getPlayer().jump();
         MovementUtil.strafe((Float)((Speed)Slack.getInstance().getModuleManager().getInstance(Speed.class)).vanillaspeed.getValue());
      }

      if (MovementUtil.isMoving()) {
         MovementUtil.strafe();
         if (!(Boolean)((Speed)Slack.getInstance().getModuleManager().getInstance(Speed.class)).vanillaGround.getValue()) {
            MovementUtil.strafe((Float)((Speed)Slack.getInstance().getModuleManager().getInstance(Speed.class)).vanillaspeed.getValue());
         }
      } else {
         MovementUtil.resetMotion(false);
      }

   }

   public String toString() {
      return "Vanilla Hop";
   }
}
