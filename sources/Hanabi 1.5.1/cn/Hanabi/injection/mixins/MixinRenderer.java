package cn.Hanabi.injection.mixins;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Render.class })
abstract class MixinRenderer<T extends Entity>
{
    @Shadow
    protected abstract boolean bindEntityTexture(final T p0);
}
