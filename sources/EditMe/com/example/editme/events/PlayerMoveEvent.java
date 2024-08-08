package com.example.editme.events;

import net.minecraft.entity.MoverType;

public class PlayerMoveEvent extends EditmeEvent {
   private double x;
   private double z;
   private double y;
   private MoverType type;

   public void setY(double var1) {
      this.y = var1;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void setType(MoverType var1) {
      this.type = var1;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public MoverType getType() {
      return this.type;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public PlayerMoveEvent(MoverType var1, double var2, double var4, double var6) {
      this.type = var1;
      this.x = var2;
      this.y = var4;
      this.z = var6;
   }

   public double getZ() {
      return this.z;
   }
}
