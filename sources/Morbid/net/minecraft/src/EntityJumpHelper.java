package net.minecraft.src;

public class EntityJumpHelper
{
    private EntityLiving entity;
    private boolean isJumping;
    
    public EntityJumpHelper(final EntityLiving par1EntityLiving) {
        this.isJumping = false;
        this.entity = par1EntityLiving;
    }
    
    public void setJumping() {
        this.isJumping = true;
    }
    
    public void doJump() {
        this.entity.setJumping(this.isJumping);
        this.isJumping = false;
    }
}
