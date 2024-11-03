package vestige.event.impl;

import vestige.event.Event;

public class EntityActionEvent extends Event {
   private boolean sprinting;
   private boolean sneaking;

   public boolean isSprinting() {
      return this.sprinting;
   }

   public boolean isSneaking() {
      return this.sneaking;
   }

   public void setSprinting(boolean sprinting) {
      this.sprinting = sprinting;
   }

   public void setSneaking(boolean sneaking) {
      this.sneaking = sneaking;
   }

   public EntityActionEvent(boolean sprinting, boolean sneaking) {
      this.sprinting = sprinting;
      this.sneaking = sneaking;
   }
}
