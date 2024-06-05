package net.minecraft.entity.boss;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityDragonPart extends Entity
{
    public final IEntityMultiPart entityDragonObj;
    public final String field_146032_b;
    
    public EntityDragonPart(final IEntityMultiPart p_i1697_1_, final String p_i1697_2_, final float p_i1697_3_, final float p_i1697_4_) {
        super(p_i1697_1_.func_82194_d());
        this.setSize(p_i1697_3_, p_i1697_4_);
        this.entityDragonObj = p_i1697_1_;
        this.field_146032_b = p_i1697_2_;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        return !this.func_180431_b(source) && this.entityDragonObj.attackEntityFromPart(this, source, amount);
    }
    
    @Override
    public boolean isEntityEqual(final Entity entityIn) {
        return this == entityIn || this.entityDragonObj == entityIn;
    }
}
