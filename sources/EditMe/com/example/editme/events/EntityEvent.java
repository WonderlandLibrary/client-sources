package com.example.editme.events;

import net.minecraft.entity.Entity;

public class EntityEvent extends EditmeEvent {
   private Entity entity;

   public EntityEvent(Entity var1) {
      this.entity = var1;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public static class EntityCollision extends EntityEvent {
      double x;
      double y;
      double z;

      public void setZ(double var1) {
         this.z = var1;
      }

      public double getZ() {
         return this.z;
      }

      public double getY() {
         return this.y;
      }

      public double getX() {
         return this.x;
      }

      public EntityCollision(Entity var1, double var2, double var4, double var6) {
         super(var1);
         this.x = var2;
         this.y = var4;
         this.z = var6;
      }

      public void setX(double var1) {
         this.x = var1;
      }

      public void setY(double var1) {
         this.y = var1;
      }
   }
}
