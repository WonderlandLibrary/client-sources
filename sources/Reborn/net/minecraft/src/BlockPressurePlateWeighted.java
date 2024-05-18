package net.minecraft.src;

import java.util.*;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate
{
    private final int maxItemsWeighted;
    
    protected BlockPressurePlateWeighted(final int par1, final String par2Str, final Material par3Material, final int par4) {
        super(par1, par2Str, par3Material);
        this.maxItemsWeighted = par4;
    }
    
    @Override
    protected int getPlateState(final World par1World, final int par2, final int par3, final int par4) {
        int var5 = 0;
        for (final EntityItem var7 : par1World.getEntitiesWithinAABB(EntityItem.class, this.getSensitiveAABB(par2, par3, par4))) {
            var5 += var7.getEntityItem().stackSize;
            if (var5 >= this.maxItemsWeighted) {
                break;
            }
        }
        if (var5 <= 0) {
            return 0;
        }
        final float var8 = Math.min(this.maxItemsWeighted, var5) / this.maxItemsWeighted;
        return MathHelper.ceiling_float_int(var8 * 15.0f);
    }
    
    @Override
    protected int getPowerSupply(final int par1) {
        return par1;
    }
    
    @Override
    protected int getMetaFromWeight(final int par1) {
        return par1;
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 10;
    }
}
