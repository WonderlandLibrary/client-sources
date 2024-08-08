package com.example.editme.events;

import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;

public class ClientPlayerAttackEvent extends EditmeEvent {
   private Entity targetEntity;

   public ClientPlayerAttackEvent(@Nonnull Entity var1) {
      if (this.targetEntity == null) {
         throw new IllegalArgumentException("Target Entity cannot be null");
      } else {
         this.targetEntity = var1;
      }
   }

   public Entity getTargetEntity() {
      return this.targetEntity;
   }
}
