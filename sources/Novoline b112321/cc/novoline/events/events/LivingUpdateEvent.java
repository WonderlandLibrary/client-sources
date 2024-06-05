package cc.novoline.events.events;

import cc.novoline.events.events.Event;
import net.minecraft.entity.Entity;

public class LivingUpdateEvent implements Event {
   private final Entity entity;

   public LivingUpdateEvent(Entity var1) {
      this.entity = var1;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
