package cc.slack.features.modules.impl.player.nofalls.basics;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import cc.slack.utils.client.mc;

public class VanillaNofall implements INoFall {
   public void onUpdate(UpdateEvent event) {
      mc.getPlayer().fallDistance = 0.0F;
   }

   public String toString() {
      return "Vanilla";
   }
}
