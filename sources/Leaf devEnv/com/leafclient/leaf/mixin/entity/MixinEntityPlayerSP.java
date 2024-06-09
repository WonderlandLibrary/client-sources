package com.leafclient.leaf.mixin.entity;

import com.leafclient.leaf.event.game.entity.*;
import com.leafclient.leaf.event.game.world.BlockPushEvent;
import com.leafclient.leaf.extension.ExtensionEntityPlayerSP;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.math.AxisAlignedBB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinEntityLivingBase implements ExtensionEntityPlayerSP {

    @Shadow public abstract boolean isSneaking();

    @Shadow public abstract void setSprinting(boolean sprinting);

    @Shadow private float lastReportedYaw;
    @Shadow private float lastReportedPitch;
    @Shadow private boolean prevOnGround;

    @Shadow @Final public NetHandlerPlayClient connection;

    @Inject(
            method = "pushOutOfBlocks",
            at = @At("INVOKE"),
            cancellable = true
    )
    private void inject$pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> info) {
        if(EventManager.INSTANCE.publish(new BlockPushEvent()).isCancelled()) {
            info.setReturnValue(false);
        }
    }

    /**
     * Injects the {@link PlayerLivingUpdateEvent}
     */
    @Inject(
            method = "onLivingUpdate",
            at = @At("HEAD")
    )
    private void inject$livingUpdate(CallbackInfo info) {
        EventManager.INSTANCE.publish(new PlayerLivingUpdateEvent());
    }

    /**
     * Injects the {@link PlayerSlowEvent.ActiveHand}
     */
    @ModifyConstant(
            method = "onLivingUpdate",
            constant = @Constant(floatValue = 0.2F)
    )
    private float modifyItemSpeed(float factor) {
        final PlayerSlowEvent.ActiveHand e = EventManager.INSTANCE.publish(new PlayerSlowEvent.ActiveHand(factor));
        if(e.isCancelled())
            return 1F;

        return e.getFactor();
    }

    private PlayerMoveEvent playerMoveEvent;

    /**
     * Injects the {@link PlayerMoveEvent}
     */
    @Redirect(
            method = "move",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"
            )
    )
    private void inject$movePre(AbstractClientPlayer entity, MoverType type, double x, double y, double z) {
        playerMoveEvent = EventManager.INSTANCE.publish(new PlayerMoveEvent(type, x, y, z));
        entity.motionX = playerMoveEvent.getX();
        entity.motionY = playerMoveEvent.getY();
        entity.motionZ = playerMoveEvent.getZ();
        super.move(type, playerMoveEvent.getX(), playerMoveEvent.getY(), playerMoveEvent.getZ());
    }

    /**
     * Injects the {@link PlayerUpdateEvent.Pre}
     */
    @Inject(
            method = "onUpdate",
            at = @At("HEAD")
    )
    private void inject$updatePre(CallbackInfo info) {
        EventManager.INSTANCE.publish(new PlayerUpdateEvent.Pre());
    }

    /**
     * Injects the {@link PlayerUpdateEvent.Pre}
     */
    @Inject(
            method = "onUpdate",
            at = @At("TAIL")
    )
    private void inject$updatePost(CallbackInfo info) {
        EventManager.INSTANCE.publish(new PlayerUpdateEvent.Post());
    }

    private PlayerMotionEvent.Pre motionEvent;

    private float eventRotationYaw, eventRotationPitch;
    private double eventPosX, eventPosY, eventPosZ;
    private boolean sneaking, sprinting, wasOnGround;

    /**
     * Injects the {@link PlayerMotionEvent.Pre}
     */
    @Inject(
            method = "onUpdateWalkingPlayer",
            at = @At("HEAD")
    )
    private void inject$walkingPlayer(CallbackInfo info) {
        final AxisAlignedBB bb = getEntityBoundingBox();
        motionEvent = EventManager.INSTANCE.publish(new PlayerMotionEvent.Pre(
                eventRotationYaw = rotationYaw,
                eventRotationPitch = rotationPitch,
                eventPosX = posX,
                eventPosY = bb.minY,
                eventPosZ = posZ,
                sneaking = isSneaking(),
                sprinting = isSprinting(),
                wasOnGround = onGround
        ));

        rotationYaw = motionEvent.getRotationYaw();
        rotationPitch = motionEvent.getRotationPitch();
        setSprinting(motionEvent.isSprinting());
        setSneaking(motionEvent.isSneaking());

        posX = motionEvent.getPosX();
        posZ = motionEvent.getPosZ();
        if(motionEvent.getPosY() != bb.minY)
            setEntityBoundingBox(bb.offset(0.0, motionEvent.getPosY() - bb.minY, 0.0));

        if(motionEvent.isHeadRotationInjected() && motionEvent.isRotationModified()) {
            rotationYawHead = motionEvent.getRotationYaw();
            renderYawOffset = motionEvent.getRotationYaw();
        }
    }

    /**
     * Injects the {@link PlayerMotionEvent.Post}
     */
    @Inject(
            method = "onUpdateWalkingPlayer",
            at = @At("TAIL")
    )
    private void inject$walkingPostPlayer(CallbackInfo info) {
        final AxisAlignedBB bb = getEntityBoundingBox();
        PlayerMotionEvent e = new PlayerMotionEvent.Post(rotationYaw, rotationPitch);

        setSprinting(sprinting);
        setSneaking(sneaking);
        onGround = wasOnGround;

        posX = eventPosX;
        posZ = eventPosZ;
        setEntityBoundingBox(getEntityBoundingBox().offset(0.0, eventPosY - bb.minY, 0.0));

        EventManager.INSTANCE.publish(e);

        if(!motionEvent.isViewLocked()) {
            rotationYaw = eventRotationYaw;
            rotationPitch = eventRotationPitch;
        }
    }

    /**
     * @author Shyrogan
     */
    @Overwrite
    public void sendChatMessage(String message) {
        PlayerChatEvent e = new PlayerChatEvent(message);
        if(EventManager.INSTANCE.publish(e).isCancelled()) {
            return;
        }

        connection.sendPacket(new CPacketChatMessage(e.getMessage()));
    }

    private boolean blocking;

    @Override
    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    @Override
    public boolean isBlocking() {
        return blocking;
    }

    @Nullable
    @Override
    public PlayerMotionEvent.Pre getLastMotionEvent() {
        return motionEvent;
    }

    @Override
    public float getLastReportedYaw() {
        return lastReportedYaw;
    }

    @Override
    public float getLastReportedPitch() {
        return lastReportedPitch;
    }

    @Override
    public boolean getPrevOnGround() {
        return prevOnGround;
    }
}
