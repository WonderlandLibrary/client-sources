package me.kaimson.melonclient.mixins.client.renderer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ bfk.class })
public interface IMixinEntityRenderer
{
    @Invoker
    void callLoadShader(final jy p0);
}
