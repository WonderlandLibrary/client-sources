package net.minecraft.client.particle;

import net.minecraft.world.*;

public static class WitchFactory implements IParticleFactory
{
    @Override
    public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
        final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        var16.setBaseSpellTextureIndex(144);
        final float var17 = worldIn.rand.nextFloat() * 0.5f + 0.35f;
        var16.setRBGColorF(1.0f * var17, 0.0f * var17, 1.0f * var17);
        return var16;
    }
}
