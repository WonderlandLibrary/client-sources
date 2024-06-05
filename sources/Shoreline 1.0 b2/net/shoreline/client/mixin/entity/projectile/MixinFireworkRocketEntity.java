package net.shoreline.client.mixin.entity.projectile;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.render.entity.RenderFireworkRocketEvent;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(FireworkRocketEntity.class)
public class MixinFireworkRocketEntity
{
    /**
     *
     * @param ci
     */
    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    private void hookTick(CallbackInfo ci)
    {
        RenderFireworkRocketEvent renderFireworkRocketEvent =
                new RenderFireworkRocketEvent();
        Shoreline.EVENT_HANDLER.dispatch(renderFireworkRocketEvent);
        if (renderFireworkRocketEvent.isCanceled())
        {
            ci.cancel();
        }
    }
}
