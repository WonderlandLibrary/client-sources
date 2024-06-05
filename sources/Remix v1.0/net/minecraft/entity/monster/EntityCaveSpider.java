package net.minecraft.entity.monster;

import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityCaveSpider extends EntitySpider
{
    public EntityCaveSpider(final World worldIn) {
        super(worldIn);
        this.setSize(0.7f, 0.5f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity p_70652_1_) {
        if (super.attackEntityAsMob(p_70652_1_)) {
            if (p_70652_1_ instanceof EntityLivingBase) {
                byte var2 = 0;
                if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
                    var2 = 7;
                }
                else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                    var2 = 15;
                }
                if (var2 > 0) {
                    ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.poison.id, var2 * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        return p_180482_2_;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.45f;
    }
}
