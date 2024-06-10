package me.kaimson.melonclient.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ bda.class })
public interface MixinPlayerControllerMP
{
    @Accessor
    void setIsHittingBlock(final boolean p0);
}
