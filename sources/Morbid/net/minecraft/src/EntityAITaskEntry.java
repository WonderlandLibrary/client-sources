package net.minecraft.src;

class EntityAITaskEntry
{
    public EntityAIBase action;
    public int priority;
    final EntityAITasks tasks;
    
    public EntityAITaskEntry(final EntityAITasks par1EntityAITasks, final int par2, final EntityAIBase par3EntityAIBase) {
        this.tasks = par1EntityAITasks;
        this.priority = par2;
        this.action = par3EntityAIBase;
    }
}
