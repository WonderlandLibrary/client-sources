package net.minecraft.entity.ai;

class EntityAITaskEntry
{
    public EntityAIBase action;
    public int priority;
    
    public EntityAITaskEntry(final int p_i1627_2_, final EntityAIBase p_i1627_3_) {
        this.priority = p_i1627_2_;
        this.action = p_i1627_3_;
    }
}
