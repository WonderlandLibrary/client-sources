package dev.stephen.nexus.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.player.EventMotionPre;
import dev.stephen.nexus.event.impl.player.*;
import dev.stephen.nexus.module.modules.movement.NoSlow;
import dev.stephen.nexus.module.modules.movement.Sprint;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {

    @Shadow
    public Input input;

    @Shadow
    public abstract Hand getActiveHand();

    @Shadow
    public boolean inSneakingPose;

    @Shadow
    public abstract void init();

    @Shadow
    public abstract boolean shouldSpawnSprintingParticles();

    @Shadow
    public float renderPitch;

    @Shadow
    protected abstract boolean isWalking();

    private EventMotionPre eventMotionPre;

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void onLivingUpdate(CallbackInfo ci) {
        Client.INSTANCE.getEventManager().post(new EventLiving());
    }

    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"), cancellable = true)
    public void EventPlayerMoveInject(MovementType type, Vec3d movement, CallbackInfo ci) {
        EventPlayerMove event = new EventPlayerMove(type, movement);
        Client.INSTANCE.getEventManager().post(event);
        type = event.getType();
        movement = event.getMovement();
        if (event.isCancelled()) {
            super.move(type, movement);
            ci.cancel();
        }
    }


    @Inject(method = "sendMovementPackets", at = @At("HEAD"), cancellable = true)
    private void sendMovementPacketsHeadInject(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        eventMotionPre = new EventMotionPre(player.getX(), player.getY(), player.getZ(), player.isOnGround());
        Client.INSTANCE.getEventManager().post(eventMotionPre);
        if (eventMotionPre.isCancelled()) ci.cancel();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getX()D"))
    private double xPositionSendEventRedirect(ClientPlayerEntity player) {
        return eventMotionPre.x;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
    private double yPositionSendEventRedirect(ClientPlayerEntity player) {
        return eventMotionPre.y;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getZ()D"))
    private double zPositionSendEventRedirect(ClientPlayerEntity player) {
        return eventMotionPre.z;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
    private boolean onGroundPositionSendEventRedirect(ClientPlayerEntity player) {
        return eventMotionPre.onGround;
    }

    @Inject(method = "sendMovementPackets", at = @At("TAIL"))
    private void sendMovementPacketsTailInject(CallbackInfo info) {
        Client.INSTANCE.getEventManager().post(new EventMotionPost());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true)
    private void onTick(CallbackInfo ci) {
        EventTickbase w = new EventTickbase();
        Client.INSTANCE.getEventManager().post(w);
        if (w.isCancelled()) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean canStartSprintingInject(boolean original) {
        if (Client.INSTANCE.getModuleManager().getModule(NoSlow.class).isEnabled()) {
            return false;
        }
        return original;
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0))
    private void tickMovementInject(CallbackInfo callbackInfo) {
        final Input input = this.input;
        if (Client.INSTANCE.getModuleManager().getModule(NoSlow.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(NoSlow.class).shouldntSlow()) {
            input.movementForward /= 0.2f;
            input.movementSideways /= 0.2f;
        }
    }

    @ModifyExpressionValue(method = {"sendMovementPackets", "tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    private float sendMovementPacketsTickInjectYaw(float original) {
        float[] rotation = Client.INSTANCE.getRotationManager().getCurrentRotation();
        if (rotation == null || !Client.INSTANCE.getRotationManager().rotating) {
            return original;
        }

        return rotation[0];
    }

    @ModifyExpressionValue(method = {"sendMovementPackets", "tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    private float sendMovementPacketsTickInjectPitch(float original) {
        float[] rotation = Client.INSTANCE.getRotationManager().getCurrentRotation();
        if (rotation == null || !Client.INSTANCE.getRotationManager().rotating) {
            return original;
        }

        return rotation[1];
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isWalking()Z"))
    private boolean tickMovementIsWalkingInject(boolean original) {
        return isOmniWalking();
    }

    @ModifyExpressionValue(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isWalking()Z"))
    private boolean canStartSprintingIsWalkingInject(boolean original) {
        return isOmniWalking();
    }

    private boolean isOmniWalking() {
        boolean hasMovement = Math.abs(input.movementForward) > 1.0E-5F || Math.abs(input.movementSideways) > 1.0E-5F;
        boolean isWalking = (double) Math.abs(input.movementForward) >= 0.8 || (double) Math.abs(input.movementSideways) >= 0.8;
        boolean modifiedIsWalking = this.isSubmergedInWater() ? hasMovement : isWalking;

        return Client.INSTANCE.getModuleManager().getModule(Sprint.class).shouldSprintDiagonally() ? modifiedIsWalking : this.isWalking();
    }
}
