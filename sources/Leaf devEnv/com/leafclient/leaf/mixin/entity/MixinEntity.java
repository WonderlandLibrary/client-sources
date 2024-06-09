package com.leafclient.leaf.mixin.entity;

import com.leafclient.leaf.event.game.entity.PlayerStepEvent;
import com.leafclient.leaf.event.game.world.BlockPushEvent;
import com.leafclient.leaf.extension.ExtensionEntity;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
@SuppressWarnings("all")
public abstract class MixinEntity implements ExtensionEntity {

    @Shadow public abstract void setSneaking(boolean sneaking);

    @Shadow public abstract void setRotationYawHead(float rotation);

    @Shadow public abstract boolean isSprinting();

    @Shadow public float rotationPitch;
    @Shadow public float rotationYaw;

    @Shadow public boolean isInWeb;

    @Shadow public void move(MoverType type, double x, double y, double z) {}

    @Shadow private AxisAlignedBB boundingBox;

    @Shadow public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow public float prevRotationYaw;
    @Shadow public boolean onGround;
    @Shadow public double posX;
    @Shadow public double posY;
    @Shadow public double posZ;
    @Shadow public double motionX;

    @Shadow public abstract AxisAlignedBB getEntityBoundingBox();

    private AxisAlignedBB bbBeforeStep;

    /**
     * Gets the bounding box and store it into a variable
     */
    @Inject(
            method = "move",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/Entity;stepHeight:F",
                    shift = At.Shift.BEFORE,
                    ordinal = 3
            )
    )
    private void inject$bbGetter(MoverType type, double x, double y, double z, CallbackInfo info) {
        bbBeforeStep = boundingBox;
    }

    /**
     * Invokes the {@link PlayerStepEvent}
     */
    @Inject(
            method = "move",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;resetPositionToBB()V",
                    shift = At.Shift.BEFORE,
                    ordinal = 1
            )
    )
    private void inject$stepEvent(MoverType type, double x, double y, double z, CallbackInfo info) {
        if(((Object)this) == Minecraft.getMinecraft().player) {
            final double xDiff = boundingBox.minX - bbBeforeStep.minX;
            final double yDiff = boundingBox.minY - bbBeforeStep.minY;
            final double zDiff = boundingBox.minZ - bbBeforeStep.minZ;

            PlayerStepEvent e = EventManager.INSTANCE.publish(new PlayerStepEvent(xDiff, yDiff, zDiff));
            if(e.isCancelled()) {
                setEntityBoundingBox(bbBeforeStep);
            }
        }
    }

    private boolean isBot;

    @Override
    public void setBot(boolean bot) {
        isBot = bot;
    }

    @Override
    public boolean isBot() {
        return isBot;
    }

    @Override
    public boolean isInWeb() {
        return isInWeb;
    }
}
