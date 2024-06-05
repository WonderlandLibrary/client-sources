package cc.novoline.events.events;

import cc.novoline.events.events.callables.CancellableEvent;
import net.minecraft.entity.Entity;

public class RenderNameTagEvent extends CancellableEvent {
   final Entity entity;

   public RenderNameTagEvent(Entity var1) {
      this.entity = var1;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
