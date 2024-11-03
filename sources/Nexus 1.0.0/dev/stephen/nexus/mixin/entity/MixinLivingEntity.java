package dev.stephen.nexus.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.player.EventJump;
import dev.stephen.nexus.event.impl.player.EventTickAI;
import dev.stephen.nexus.module.modules.render.Animations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow
    public abstract float getYaw(float tickDelta);

    @Shadow
    public abstract void remove(RemovalReason reason);

    @Shadow
    protected abstract Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput);

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public int jumpingCooldown;

    @Inject(method = "getHandSwingDuration", at = {@At("HEAD")}, cancellable = true)
    private void getHandSwingDurationInject(final CallbackInfoReturnable<Integer> info) {
        if (Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled())
            info.setReturnValue(Animations.swingSpeed.getValueInt());
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovementInject(CallbackInfo callbackInfo) {
        jumpingCooldown = 0;
    }

    @Inject(method = "tickNewAi", at = @At("HEAD"))
    private void hookAITIck(CallbackInfo callbackInfo) {
        Client.INSTANCE.getEventManager().post(new EventTickAI());
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"), slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F", ordinal = 1)))
    private float tickInject(float original) {
        if ((Object) this != MinecraftClient.getInstance().player) {
            return original;
        }

        return Client.INSTANCE.getRotationManager().rotating ? Client.INSTANCE.getRotationManager().yaw : original;
    }

    @ModifyExpressionValue(method = "turnHead", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    private float turnHeadInject(float original) {
        if ((Object) this != MinecraftClient.getInstance().player) {
            return original;
        }

        return Client.INSTANCE.getRotationManager().rotating ? Client.INSTANCE.getRotationManager().yaw : original;
    }

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F", ordinal = 0))
    private float jumpInject(LivingEntity entity) {
        if ((Object) this == MinecraftClient.getInstance().player) {
            EventJump eventJump = new EventJump(entity.getYaw());
            Client.INSTANCE.getEventManager().post(eventJump);
            return eventJump.getYaw();
        }
        return entity.getYaw();
    }
}
