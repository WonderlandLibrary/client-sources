package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;

class AIFireballAttack extends EntityAIBase
{
    private EntityBlaze field_179469_a;
    private int field_179467_b;
    private int field_179468_c;
    
    public AIFireballAttack() {
        this.field_179469_a = EntityBlaze.this;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase var1 = this.field_179469_a.getAttackTarget();
        return var1 != null && var1.isEntityAlive();
    }
    
    @Override
    public void startExecuting() {
        this.field_179467_b = 0;
    }
    
    @Override
    public void resetTask() {
        this.field_179469_a.func_70844_e(false);
    }
    
    @Override
    public void updateTask() {
        --this.field_179468_c;
        final EntityLivingBase var1 = this.field_179469_a.getAttackTarget();
        final double var2 = this.field_179469_a.getDistanceSqToEntity(var1);
        if (var2 < 4.0) {
            if (this.field_179468_c <= 0) {
                this.field_179468_c = 20;
                this.field_179469_a.attackEntityAsMob(var1);
            }
            this.field_179469_a.getMoveHelper().setMoveTo(var1.posX, var1.posY, var1.posZ, 1.0);
        }
        else if (var2 < 256.0) {
            final double var3 = var1.posX - this.field_179469_a.posX;
            final double var4 = var1.getEntityBoundingBox().minY + var1.height / 2.0f - (this.field_179469_a.posY + this.field_179469_a.height / 2.0f);
            final double var5 = var1.posZ - this.field_179469_a.posZ;
            if (this.field_179468_c <= 0) {
                ++this.field_179467_b;
                if (this.field_179467_b == 1) {
                    this.field_179468_c = 60;
                    this.field_179469_a.func_70844_e(true);
                }
                else if (this.field_179467_b <= 4) {
                    this.field_179468_c = 6;
                }
                else {
                    this.field_179468_c = 100;
                    this.field_179467_b = 0;
                    this.field_179469_a.func_70844_e(false);
                }
                if (this.field_179467_b > 1) {
                    final float var6 = MathHelper.sqrt_float(MathHelper.sqrt_double(var2)) * 0.5f;
                    this.field_179469_a.worldObj.playAuxSFXAtEntity(null, 1009, new BlockPos((int)this.field_179469_a.posX, (int)this.field_179469_a.posY, (int)this.field_179469_a.posZ), 0);
                    for (int var7 = 0; var7 < 1; ++var7) {
                        final EntitySmallFireball var8 = new EntitySmallFireball(this.field_179469_a.worldObj, this.field_179469_a, var3 + this.field_179469_a.getRNG().nextGaussian() * var6, var4, var5 + this.field_179469_a.getRNG().nextGaussian() * var6);
                        var8.posY = this.field_179469_a.posY + this.field_179469_a.height / 2.0f + 0.5;
                        this.field_179469_a.worldObj.spawnEntityInWorld(var8);
                    }
                }
            }
            this.field_179469_a.getLookHelper().setLookPositionWithEntity(var1, 10.0f, 10.0f);
        }
        else {
            this.field_179469_a.getNavigator().clearPathEntity();
            this.field_179469_a.getMoveHelper().setMoveTo(var1.posX, var1.posY, var1.posZ, 1.0);
        }
        super.updateTask();
    }
}
