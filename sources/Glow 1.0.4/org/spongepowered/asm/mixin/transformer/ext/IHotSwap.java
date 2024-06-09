package org.spongepowered.asm.mixin.transformer.ext;

public interface IHotSwap
{
    void registerMixinClass(final String p0);
    
    void registerTargetClass(final String p0, final byte[] p1);
}
