package vestige.event.impl;

import net.minecraft.entity.Entity;
import vestige.event.Event;

public class EntityMoveEvent extends Event {
   private Entity entity;

   public Entity getEntity() {
      return this.entity;
   }

   public EntityMoveEvent(Entity entity) {
      this.entity = entity;
   }
}
