package net.minecraft.entity.ai;

public abstract class EntityAIBase {
   private static final String __OBFID = "CL_00001587";
   private int mutexBits;

   public void updateTask() {
   }

   public int getMutexBits() {
      return this.mutexBits;
   }

   public boolean isInterruptible() {
      return true;
   }

   public void resetTask() {
   }

   public abstract boolean shouldExecute();

   public boolean continueExecuting() {
      return this.shouldExecute();
   }

   public void startExecuting() {
   }

   public void setMutexBits(int var1) {
      this.mutexBits = var1;
   }
}
