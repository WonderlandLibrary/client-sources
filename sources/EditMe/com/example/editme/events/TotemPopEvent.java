package com.example.editme.events;

import net.minecraft.entity.Entity;

public class TotemPopEvent extends EditmeEvent {
   private Entity entity;

   public TotemPopEvent(Entity var1) {
      this.entity = var1;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
