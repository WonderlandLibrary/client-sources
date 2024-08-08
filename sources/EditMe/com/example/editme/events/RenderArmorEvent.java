package com.example.editme.events;

import net.minecraft.entity.EntityLivingBase;

public class RenderArmorEvent extends EditmeEvent {
   public EntityLivingBase entity;

   public RenderArmorEvent(EntityLivingBase var1) {
      this.entity = var1;
   }
}
