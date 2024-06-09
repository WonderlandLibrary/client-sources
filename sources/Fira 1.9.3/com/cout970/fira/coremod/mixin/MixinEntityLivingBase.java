package com.cout970.fira.coremod.mixin;

import com.cout970.fira.modules.ElytraFly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

    private double tmpMotionX = 0.0;
    private double tmpMotionZ = 0.0;

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    @Inject(
        method = "travel",
        slice = {@Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/EntityLivingBase;isElytraFlying()Z",
                ordinal = 0
            ),
            to = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/EntityLivingBase;move(Lnet/minecraft/entity/MoverType;DDD)V",
                ordinal = 0
            )
        )},
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/MoverType;SELF:Lnet/minecraft/entity/MoverType;",
            shift = At.Shift.BY,
            by = -2,
            ordinal = 0
        )
    )
    public void preMove(float x, float y, float z, CallbackInfo ci) {
        ElytraFly.INSTANCE.elytraTravelHook(this);
    }

    @Inject(
        method = "travel",
        slice = {@Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/EntityLivingBase;isElytraFlying()Z",
                ordinal = 0
            ),
            to = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/EntityLivingBase;move(Lnet/minecraft/entity/MoverType;DDD)V",
                ordinal = 0
            )
        )},
        at = @At(
            value = "JUMP",
            opcode = Opcodes.IFLE,
            shift = At.Shift.BY,
            by = -4,
            ordinal = 2
        )
    )
    public void preHeadMove(CallbackInfo ci) {
        tmpMotionX = this.motionX;
        tmpMotionZ = this.motionZ;
    }

    @Inject(
        method = "travel",
        slice = {@Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/EntityLivingBase;isElytraFlying()Z",
                ordinal = 0
            ),
            to = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/EntityLivingBase;move(Lnet/minecraft/entity/MoverType;DDD)V",
                ordinal = 0
            )
        )},
        at = @At(
            value = "CONSTANT",
            args = {
                "doubleValue=0.9900000095367432",
            },
            shift = At.Shift.BY,
            by = -3,
            ordinal = 0
        )
    )
    public void postHeadMove(CallbackInfo ci) {
        if (!ElytraFly.INSTANCE.preElytraTravelHook(this)) {
            this.motionX = tmpMotionX;
            this.motionZ = tmpMotionZ;
        }
    }
}
