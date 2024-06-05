package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;

class AIFindPlayer extends EntityAINearestAttackableTarget
{
    private EntityPlayer field_179448_g;
    private int field_179450_h;
    private int field_179451_i;
    private EntityEnderman field_179449_j;
    
    public AIFindPlayer() {
        super(EntityEnderman.this, EntityPlayer.class, true);
        this.field_179449_j = EntityEnderman.this;
    }
    
    @Override
    public boolean shouldExecute() {
        final double var1 = this.getTargetDistance();
        final List var2 = this.taskOwner.worldObj.func_175647_a(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(var1, 4.0, var1), this.targetEntitySelector);
        Collections.sort((List<Object>)var2, this.theNearestAttackableTargetSorter);
        if (var2.isEmpty()) {
            return false;
        }
        this.field_179448_g = var2.get(0);
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.field_179450_h = 5;
        this.field_179451_i = 0;
    }
    
    @Override
    public void resetTask() {
        this.field_179448_g = null;
        this.field_179449_j.setScreaming(false);
        final IAttributeInstance var1 = this.field_179449_j.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        var1.removeModifier(EntityEnderman.access$000());
        super.resetTask();
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.field_179448_g == null) {
            return super.continueExecuting();
        }
        if (!EntityEnderman.access$100(this.field_179449_j, this.field_179448_g)) {
            return false;
        }
        EntityEnderman.access$202(this.field_179449_j, true);
        this.field_179449_j.faceEntity(this.field_179448_g, 10.0f, 10.0f);
        return true;
    }
    
    @Override
    public void updateTask() {
        if (this.field_179448_g != null) {
            if (--this.field_179450_h <= 0) {
                this.targetEntity = this.field_179448_g;
                this.field_179448_g = null;
                super.startExecuting();
                this.field_179449_j.playSound("mob.endermen.stare", 1.0f, 1.0f);
                this.field_179449_j.setScreaming(true);
                final IAttributeInstance var1 = this.field_179449_j.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                var1.applyModifier(EntityEnderman.access$000());
            }
        }
        else {
            if (this.targetEntity != null) {
                if (this.targetEntity instanceof EntityPlayer && EntityEnderman.access$100(this.field_179449_j, (EntityPlayer)this.targetEntity)) {
                    if (this.targetEntity.getDistanceSqToEntity(this.field_179449_j) < 16.0) {
                        this.field_179449_j.teleportRandomly();
                    }
                    this.field_179451_i = 0;
                }
                else if (this.targetEntity.getDistanceSqToEntity(this.field_179449_j) > 256.0 && this.field_179451_i++ >= 30 && this.field_179449_j.teleportToEntity(this.targetEntity)) {
                    this.field_179451_i = 0;
                }
            }
            super.updateTask();
        }
    }
}
