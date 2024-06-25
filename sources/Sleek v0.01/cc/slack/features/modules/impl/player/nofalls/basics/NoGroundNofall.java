package cc.slack.features.modules.impl.player.nofalls.basics;

import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;

public class NoGroundNofall implements INoFall {
   public void onMotion(MotionEvent event) {
      event.setGround(false);
   }

   public String toString() {
      return "No Ground";
   }
}
