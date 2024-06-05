package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class SlimeMoveHelper extends EntityMoveHelper
{
    private float field_179922_g;
    private int field_179924_h;
    private EntitySlime field_179925_i;
    private boolean field_179923_j;
    
    public SlimeMoveHelper() {
        super(EntitySlime.this);
        this.field_179925_i = EntitySlime.this;
    }
    
    public void func_179920_a(final float p_179920_1_, final boolean p_179920_2_) {
        this.field_179922_g = p_179920_1_;
        this.field_179923_j = p_179920_2_;
    }
    
    public void func_179921_a(final double p_179921_1_) {
        this.speed = p_179921_1_;
        this.update = true;
    }
    
    @Override
    public void onUpdateMoveHelper() {
        this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0f);
        this.entity.rotationYawHead = this.entity.rotationYaw;
        this.entity.renderYawOffset = this.entity.rotationYaw;
        if (!this.update) {
            this.entity.setMoveForward(0.0f);
        }
        else {
            this.update = false;
            if (this.entity.onGround) {
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                if (this.field_179924_h-- <= 0) {
                    this.field_179924_h = this.field_179925_i.getJumpDelay();
                    if (this.field_179923_j) {
                        this.field_179924_h /= 3;
                    }
                    this.field_179925_i.getJumpHelper().setJumping();
                    if (this.field_179925_i.makesSoundOnJump()) {
                        this.field_179925_i.playSound(this.field_179925_i.getJumpSound(), this.field_179925_i.getSoundVolume(), ((this.field_179925_i.getRNG().nextFloat() - this.field_179925_i.getRNG().nextFloat()) * 0.2f + 1.0f) * 0.8f);
                    }
                }
                else {
                    final EntitySlime field_179925_i = this.field_179925_i;
                    final EntitySlime field_179925_i2 = this.field_179925_i;
                    final float n = 0.0f;
                    field_179925_i2.moveForward = n;
                    field_179925_i.moveStrafing = n;
                    this.entity.setAIMoveSpeed(0.0f);
                }
            }
            else {
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
            }
        }
    }
}
