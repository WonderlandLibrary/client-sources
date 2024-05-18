package net.minecraft.src;

import java.util.*;

public class BlockPressurePlate extends BlockBasePressurePlate
{
    private EnumMobType triggerMobType;
    
    protected BlockPressurePlate(final int par1, final String par2Str, final Material par3Material, final EnumMobType par4EnumMobType) {
        super(par1, par2Str, par3Material);
        this.triggerMobType = par4EnumMobType;
    }
    
    @Override
    protected int getMetaFromWeight(final int par1) {
        return (par1 > 0) ? 1 : 0;
    }
    
    @Override
    protected int getPowerSupply(final int par1) {
        return (par1 == 1) ? 15 : 0;
    }
    
    @Override
    protected int getPlateState(final World par1World, final int par2, final int par3, final int par4) {
        List var5 = null;
        if (this.triggerMobType == EnumMobType.everything) {
            var5 = par1World.getEntitiesWithinAABBExcludingEntity(null, this.getSensitiveAABB(par2, par3, par4));
        }
        if (this.triggerMobType == EnumMobType.mobs) {
            var5 = par1World.getEntitiesWithinAABB(EntityLiving.class, this.getSensitiveAABB(par2, par3, par4));
        }
        if (this.triggerMobType == EnumMobType.players) {
            var5 = par1World.getEntitiesWithinAABB(EntityPlayer.class, this.getSensitiveAABB(par2, par3, par4));
        }
        if (!var5.isEmpty()) {
            for (final Entity var7 : var5) {
                if (!var7.doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }
        return 0;
    }
}
