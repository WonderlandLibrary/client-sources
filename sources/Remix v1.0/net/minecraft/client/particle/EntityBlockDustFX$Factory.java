package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public static class Factory implements IParticleFactory
{
    @Override
    public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
        final IBlockState var16 = Block.getStateById(p_178902_15_[0]);
        return (var16.getBlock().getRenderType() == -1) ? null : new EntityBlockDustFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, var16).func_174845_l();
    }
}
