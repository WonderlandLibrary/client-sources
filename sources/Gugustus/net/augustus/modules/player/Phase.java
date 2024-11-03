package net.augustus.modules.player;

import java.awt.Color;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;

public class Phase extends Module {
   private final TimeHelper tickTimeHelper = new TimeHelper();

   public Phase() {
      super("Phase", new Color(45, 196, 148), Categorys.PLAYER);
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      mc.thePlayer.motionY = 0.0;
      mc.thePlayer.onGround = true;
   }
}
