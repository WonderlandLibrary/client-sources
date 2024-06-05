package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityJumpHelper
{
    protected boolean isJumping;
    private EntityLiving entity;
    
    public EntityJumpHelper(final EntityLiving p_i1612_1_) {
        this.entity = p_i1612_1_;
    }
    
    public void setJumping() {
        this.isJumping = true;
    }
    
    public void doJump() {
        this.entity.setJumping(this.isJumping);
        this.isJumping = false;
    }
}
