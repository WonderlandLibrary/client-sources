package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public static class Factory implements IParticleFactory
{
    @Override
    public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
        return new Barrier(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Item.getItemFromBlock(Blocks.barrier));
    }
}
