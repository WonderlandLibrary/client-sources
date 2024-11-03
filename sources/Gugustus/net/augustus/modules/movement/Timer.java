package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventPreMotion;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;

public class Timer extends Module {
   public DoubleValue timerSpeed = new DoubleValue(1, "Time", this, 1.0, 0.1, 10.0, 2);

   public Timer() {
      super("Timer", new Color(180, 176, 119), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventPreMotion(EventPreMotion eventPreMotion) {
      mc.getTimer().timerSpeed = (float)this.timerSpeed.getValue();
   }

   @Override
   public void onDisable() {
      mc.getTimer().timerSpeed = 1.0F;
   }

   @EventTarget
   public void onEventWord(EventWorld eventWorld) {
      if (eventWorld.getWorldClient() == null) {
         this.toggle();
      }
   }
}
