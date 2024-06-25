package cc.slack.events.impl.player;

import cc.slack.events.Event;
import net.minecraft.entity.Entity;

public class AttackEvent extends Event {
   private Entity targetEntity;

   public Entity getTargetEntity() {
      return this.targetEntity;
   }

   public void setTargetEntity(Entity targetEntity) {
      this.targetEntity = targetEntity;
   }

   public AttackEvent(Entity targetEntity) {
      this.targetEntity = targetEntity;
   }
}
