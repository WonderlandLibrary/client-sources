package net.minecraft.client.particle;

import net.minecraft.world.*;

public static class MagicFactory implements IParticleFactory
{
    @Override
    public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
        final EntityCrit2FX var16 = new EntityCrit2FX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        var16.setRBGColorF(var16.getRedColorF() * 0.3f, var16.getGreenColorF() * 0.8f, var16.getBlueColorF());
        var16.nextTextureIndexX();
        return var16;
    }
}
