package com.example.editme.util.client;

import net.minecraft.entity.Entity;

public class Dmg {
   private float damage;
   private int ticks;
   private Entity entity;

   public int tick() {
      --this.ticks;
      return this.ticks;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public float getDamage() {
      return this.damage;
   }

   public Dmg(Entity var1, float var2, int var3) {
      this.entity = var1;
      this.damage = var2;
      this.ticks = var3;
   }
}
