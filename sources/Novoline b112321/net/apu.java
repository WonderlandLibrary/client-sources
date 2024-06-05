package net;

import cc.novoline.events.events.Event;
import net.aSv;

public class apu implements Event {
   private final float a;

   public apu(float var1) {
      this.a = var1;
   }

   @aSv
   public float getPartialTicks() {
      return this.a;
   }
}
