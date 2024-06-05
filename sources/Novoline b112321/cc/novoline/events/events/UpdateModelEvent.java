package cc.novoline.events.events;

import cc.novoline.events.events.Event;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;

public class UpdateModelEvent implements Event {
   private ModelPlayer modelPlayer;
   private Entity entity;

   public UpdateModelEvent(Entity var1, ModelPlayer var2) {
      this.modelPlayer = var2;
      this.entity = var1;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public ModelPlayer getModelPlayer() {
      return this.modelPlayer;
   }
}
