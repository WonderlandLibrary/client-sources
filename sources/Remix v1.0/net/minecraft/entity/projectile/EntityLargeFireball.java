package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;

public class EntityLargeFireball extends EntityFireball
{
    public int field_92057_e;
    
    public EntityLargeFireball(final World worldIn) {
        super(worldIn);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World worldIn, final double p_i1768_2_, final double p_i1768_4_, final double p_i1768_6_, final double p_i1768_8_, final double p_i1768_10_, final double p_i1768_12_) {
        super(worldIn, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World worldIn, final EntityLivingBase p_i1769_2_, final double p_i1769_3_, final double p_i1769_5_, final double p_i1769_7_) {
        super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
        this.field_92057_e = 1;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p_70227_1_) {
        if (!this.worldObj.isRemote) {
            if (p_70227_1_.entityHit != null) {
                p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0f);
                this.func_174815_a(this.shootingEntity, p_70227_1_.entityHit);
            }
            final boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, (float)this.field_92057_e, var2, var2);
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("ExplosionPower", this.field_92057_e);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        if (tagCompund.hasKey("ExplosionPower", 99)) {
            this.field_92057_e = tagCompund.getInteger("ExplosionPower");
        }
    }
}
