package com.client.glowclient.sponge.mixin;

import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ EntityPig.class })
public abstract class MixinEntityPig extends EntityAnimal
{
    @Shadow
    private boolean field_184765_bx;
    @Shadow
    private int field_184766_bz;
    @Shadow
    private int field_184767_bA;
    
    public MixinEntityPig() {
        super((World)null);
    }
    
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    public void preCanBeSteered(final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v5) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
    
    @Overwrite
    public void travel(final float n, final float n2, final float n3) {
        final Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
        if (this.isBeingRidden() && this.canBeSteered()) {
            this.rotationYaw = entity.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            this.stepHeight = 1.0f;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
            if (this.boosting && this.boostTime++ > this.totalBoostTime) {
                this.boosting = false;
            }
            if (this.canPassengerSteer()) {
                float aiMoveSpeed;
                if (!HookTranslator.v5) {
                    aiMoveSpeed = (float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 0.225f;
                }
                else {
                    aiMoveSpeed = 0.0f;
                }
                if (this.boosting && !HookTranslator.v5) {
                    aiMoveSpeed += aiMoveSpeed * 1.15f * MathHelper.sin(this.boostTime / (float)this.totalBoostTime * 3.1415927f);
                }
                this.setAIMoveSpeed(aiMoveSpeed);
                super.travel(0.0f, 0.0f, 1.0f);
            }
            else {
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double n4 = this.posX - this.prevPosX;
            final double n5 = this.posZ - this.prevPosZ;
            float n6 = MathHelper.sqrt(n4 * n4 + n5 * n5) * 4.0f;
            if (n6 > 1.0f) {
                n6 = 1.0f;
            }
            this.limbSwingAmount += (n6 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
        }
        else {
            this.stepHeight = 0.5f;
            this.jumpMovementFactor = 0.02f;
            super.travel(n, n2, n3);
        }
    }
}
