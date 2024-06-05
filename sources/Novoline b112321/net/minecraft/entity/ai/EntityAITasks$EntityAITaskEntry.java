package net.minecraft.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;

class EntityAITasks$EntityAITaskEntry {
   public EntityAIBase action;
   public int priority;

   public EntityAITasks$EntityAITaskEntry(int var1, EntityAIBase var2) {
      this.priority = var1;
      this.action = var2;
   }
}
