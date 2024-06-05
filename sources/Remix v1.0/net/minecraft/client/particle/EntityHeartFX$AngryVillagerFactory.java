package net.minecraft.client.particle;

import net.minecraft.world.*;

public static class AngryVillagerFactory implements IParticleFactory
{
    @Override
    public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
        final EntityHeartFX var16 = new EntityHeartFX(worldIn, p_178902_3_, p_178902_5_ + 0.5, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        var16.setParticleTextureIndex(81);
        var16.setRBGColorF(1.0f, 1.0f, 1.0f);
        return var16;
    }
}
