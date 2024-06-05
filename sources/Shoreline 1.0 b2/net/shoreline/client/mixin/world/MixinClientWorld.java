package net.shoreline.client.mixin.world;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.world.AddEntityEvent;
import net.shoreline.client.impl.event.world.RemoveEntityEvent;
import net.shoreline.client.impl.event.world.SkyboxEvent;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(ClientWorld.class)
public class MixinClientWorld
{
    /**
     *
     * @param id
     * @param entity
     * @param ci
     */
    @Inject(method = "addEntity", at = @At(value = "HEAD"))
    private void hookAddEntity(int id, Entity entity, CallbackInfo ci)
    {
        AddEntityEvent addEntityEvent = new AddEntityEvent(id, entity);
        Shoreline.EVENT_HANDLER.dispatch(addEntityEvent);
    }

    /**
     *
     * @param entityId
     * @param removalReason
     * @param ci
     */
    @Inject(method = "removeEntity", at = @At(value = "HEAD"))
    private void hookRemoveEntity(int entityId, Entity.RemovalReason removalReason,
                                  CallbackInfo ci)
    {
        final RemoveEntityEvent removeEntityEvent = new RemoveEntityEvent(
                entityId, removalReason);
        Shoreline.EVENT_HANDLER.dispatch(removeEntityEvent);
    }

    /**
     *
     * @param cameraPos
     * @param tickDelta
     * @param cir
     */
    @Inject(method = "getSkyColor", at = @At(value = "HEAD"), cancellable = true)
    private void hookGetSkyColor(Vec3d cameraPos, float tickDelta,
                                 CallbackInfoReturnable<Vec3d> cir)
    {
        SkyboxEvent.Sky skyboxEvent = new SkyboxEvent.Sky();
        Shoreline.EVENT_HANDLER.dispatch(skyboxEvent);
        if (skyboxEvent.isCanceled())
        {
            cir.cancel();
            cir.setReturnValue(skyboxEvent.getColor());
        }
    }

    /**
     *
     * @param tickDelta
     * @param cir
     */
    @Inject(method = "getCloudsColor", at = @At(value = "HEAD"), cancellable = true)
    private void hookGetCloudsColor(float tickDelta,
                                    CallbackInfoReturnable<Vec3d> cir)
    {
        SkyboxEvent.Cloud skyboxEvent = new SkyboxEvent.Cloud();
        Shoreline.EVENT_HANDLER.dispatch(skyboxEvent);
        if (skyboxEvent.isCanceled())
        {
            cir.cancel();
            cir.setReturnValue(skyboxEvent.getColor());
        }
    }
}
