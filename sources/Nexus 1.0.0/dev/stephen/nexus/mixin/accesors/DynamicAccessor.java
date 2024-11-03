package dev.stephen.nexus.mixin.accesors;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderTickCounter.Dynamic.class)
public interface DynamicAccessor {
    @Accessor
    float getTickTime();

    @Accessor
    float getTickDelta();
}
