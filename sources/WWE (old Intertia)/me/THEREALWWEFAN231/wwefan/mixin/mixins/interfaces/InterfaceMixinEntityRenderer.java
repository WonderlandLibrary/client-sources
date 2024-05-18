package me.THEREALWWEFAN231.wwefan.mixin.mixins.interfaces;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(value = { EntityRenderer.class }, priority = 9999)
public interface InterfaceMixinEntityRenderer
{
    @Invoker("orientCamera")
    void orientCameraVoid(final float p0);
}
