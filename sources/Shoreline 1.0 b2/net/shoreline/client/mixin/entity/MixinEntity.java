package net.shoreline.client.mixin.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.entity.UpdateVelocityEvent;
import net.shoreline.client.impl.event.entity.VelocityMultiplierEvent;
import net.shoreline.client.impl.event.entity.player.PushEntityEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.Globals;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Entity
 */
@Mixin(Entity.class)
public abstract class MixinEntity implements Globals
{
    /**
     *
     * @param movementInput
     * @param speed
     * @param yaw
     * @return
     */
    @Shadow
    protected static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw)
    {
        return null;
    }

    /**
     *
     *
     * @param instance
     * @return
     */
    @Redirect(method = "getVelocityMultiplier", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;getBlock()" +
                    "Lnet/minecraft/ block/Block;"))
    private Block hookGetVelocityMultiplier(BlockState instance)
    {
        if ((Object) this != mc.player)
        {
            return instance.getBlock();
        }
        VelocityMultiplierEvent velocityMultiplierEvent =
                new VelocityMultiplierEvent(instance);
        Shoreline.EVENT_HANDLER.dispatch(velocityMultiplierEvent);
        if (velocityMultiplierEvent.isCanceled())
        {
            return Blocks.DIRT;
        }
        return instance.getBlock();
    }

    /**
     *
     * @param speed
     * @param movementInput
     * @param ci
     */
    @Inject(method = "updateVelocity", at = @At(value = "HEAD"), cancellable = true)
    private void hookUpdateVelocity(float speed, Vec3d movementInput, CallbackInfo ci)
    {
        if ((Object) this == mc.player)
        {
            UpdateVelocityEvent updateVelocityEvent = new UpdateVelocityEvent(speed);
            Shoreline.EVENT_HANDLER.dispatch(updateVelocityEvent);
            if (updateVelocityEvent.isCanceled())
            {
                ci.cancel();
                Vec3d vec3d = movementInputToVelocity(movementInput, speed, updateVelocityEvent.getYaw());
                mc.player.setVelocity(mc.player.getVelocity().add(vec3d));
            }
        }
    }

    /**
     *
     * @param entity
     * @param ci
     */
    @Inject(method = "pushAwayFrom", at = @At(value = "HEAD"), cancellable = true)
    private void hookPushAwayFrom(Entity entity, CallbackInfo ci)
    {
        PushEntityEvent pushEntityEvent = new PushEntityEvent();
        Shoreline.EVENT_HANDLER.dispatch(pushEntityEvent);
        if (pushEntityEvent.isCanceled())
        {
            ci.cancel();
        }
    }
}
