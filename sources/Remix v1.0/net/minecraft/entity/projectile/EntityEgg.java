package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityEgg extends EntityThrowable
{
    public EntityEgg(final World worldIn) {
        super(worldIn);
    }
    
    public EntityEgg(final World worldIn, final EntityLivingBase p_i1780_2_) {
        super(worldIn, p_i1780_2_);
    }
    
    public EntityEgg(final World worldIn, final double p_i1781_2_, final double p_i1781_4_, final double p_i1781_6_) {
        super(worldIn, p_i1781_2_, p_i1781_4_, p_i1781_6_);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p_70184_1_) {
        if (p_70184_1_.entityHit != null) {
            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0f);
        }
        if (!this.worldObj.isRemote && this.rand.nextInt(8) == 0) {
            byte var2 = 1;
            if (this.rand.nextInt(32) == 0) {
                var2 = 4;
            }
            for (int var3 = 0; var3 < var2; ++var3) {
                final EntityChicken var4 = new EntityChicken(this.worldObj);
                var4.setGrowingAge(-24000);
                var4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                this.worldObj.spawnEntityInWorld(var4);
            }
        }
        final double var5 = 0.08;
        for (int var6 = 0; var6 < 8; ++var6) {
            this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, (this.rand.nextFloat() - 0.5) * 0.08, (this.rand.nextFloat() - 0.5) * 0.08, (this.rand.nextFloat() - 0.5) * 0.08, Item.getIdFromItem(Items.egg));
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
