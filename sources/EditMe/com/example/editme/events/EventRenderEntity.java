package com.example.editme.events;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class EventRenderEntity extends EditmeEvent {
   private final float partialTicks;
   private final Render renderer;
   private final double y;
   private final double x;
   private final double z;
   private final Entity entity;
   private final float entityYaw;

   public double getZ() {
      return this.z;
   }

   public EventRenderEntity(Render var1, Entity var2, double var3, double var5, double var7, float var9, float var10) {
      this.renderer = var1;
      this.entity = var2;
      this.x = var3;
      this.y = var5;
      this.z = var7;
      this.entityYaw = var9;
      this.partialTicks = var10;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public double getY() {
      return this.y;
   }

   public float getEntityYaw() {
      return this.entityYaw;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public Render getRenderer() {
      return this.renderer;
   }

   public double getX() {
      return this.x;
   }
}
