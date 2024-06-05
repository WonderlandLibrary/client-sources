package net.minecraft.src;

public abstract class EntityAIBase
{
    private int mutexBits;
    
    public EntityAIBase() {
        this.mutexBits = 0;
    }
    
    public abstract boolean shouldExecute();
    
    public boolean continueExecuting() {
        return this.shouldExecute();
    }
    
    public boolean isInterruptible() {
        return true;
    }
    
    public void startExecuting() {
    }
    
    public void resetTask() {
    }
    
    public void updateTask() {
    }
    
    public void setMutexBits(final int par1) {
        this.mutexBits = par1;
    }
    
    public int getMutexBits() {
        return this.mutexBits;
    }
}
