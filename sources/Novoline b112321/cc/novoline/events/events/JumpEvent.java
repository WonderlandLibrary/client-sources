package cc.novoline.events.events;

import cc.novoline.events.events.callables.CancellableEvent;

public class JumpEvent extends CancellableEvent {
   private double height;

   public JumpEvent(double var1) {
      this.height = var1;
   }

   public JumpEvent() {
   }

   public void setHeight(double var1) {
      this.height = var1;
   }

   public double getHeight() {
      return this.height;
   }
}
