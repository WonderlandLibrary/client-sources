package org.spongepowered.asm.mixin.transformer;

interface IListener
{
    void onPrepare(final MixinInfo p0);
    
    void onInit(final MixinInfo p0);
}
