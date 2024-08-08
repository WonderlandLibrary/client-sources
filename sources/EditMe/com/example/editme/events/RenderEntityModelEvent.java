package com.example.editme.events;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class RenderEntityModelEvent extends EditmeEvent {
   public Float scale;
   public Float age;
   public Float headPitch;
   public ModelBase modelBase;
   public Float limbSwingAmount;
   public Float limbSwing;
   public Entity entity;
   public Float headYaw;

   public RenderEntityModelEvent(ModelBase var1, Entity var2, Float var3, Float var4, Float var5, Float var6, Float var7, Float var8) {
      this.modelBase = var1;
      this.entity = var2;
      this.limbSwing = var3;
      this.limbSwingAmount = var4;
      this.age = var5;
      this.headYaw = var6;
      this.headPitch = var7;
      this.scale = var8;
   }
}
