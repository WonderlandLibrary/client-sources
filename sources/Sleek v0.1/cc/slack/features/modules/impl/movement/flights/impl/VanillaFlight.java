package cc.slack.features.modules.impl.movement.flights.impl;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;

public class VanillaFlight implements IFlight {
   public void onDisable() {
      MovementUtil.resetMotion(true);
   }

   public void onMove(MoveEvent event) {
      event.setY(mc.getGameSettings().keyBindJump.isKeyDown() ? 3.32D : (mc.getGameSettings().keyBindSneak.isKeyDown() ? -3.32D : 0.0D));
      MovementUtil.setSpeed(event, 3.5D);
   }

   public String toString() {
      return "Vanilla";
   }
}
