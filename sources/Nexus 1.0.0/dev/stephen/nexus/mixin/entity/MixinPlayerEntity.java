package dev.stephen.nexus.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.player.EventTravel;
import dev.stephen.nexus.module.modules.movement.KeepSprint;
import dev.stephen.nexus.module.modules.player.BedAura;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

@Mixin(PlayerEntity.class)
@SuppressWarnings(value = "all")
public abstract class MixinPlayerEntity extends LivingEntity{

    @Shadow
    @Final
    private PlayerInventory inventory;

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        if (!(Client.INSTANCE.getModuleManager().getModule(BedAura.class).isEnabled() && BedAura.bedPos != null && BedAura.ignoreGround.getValue())) {
            return;
        }

        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player == MinecraftClient.getInstance().player) {
            if (!player.isOnGround()) {
                float modifiedSpeed = cir.getReturnValue() * 5.0F;
                cir.setReturnValue(modifiedSpeed);
            }
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"), cancellable = true)
    private void attackInject(Entity target, CallbackInfo ci) {
        if ((Object) this == MinecraftClient.getInstance().player && Client.INSTANCE.getModuleManager().getModule(KeepSprint.class).isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V"), cancellable = true)
    private void attackInject2(Entity target, CallbackInfo ci) {
        if ((Object) this == MinecraftClient.getInstance().player && Client.INSTANCE.getModuleManager().getModule(KeepSprint.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(KeepSprint.class).sprint.getValue()) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            player.setSprinting(true);
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "tickNewAi", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getYaw()F"))
    private float tickNewAiInject2(float original) {
        if ((Object) this != MinecraftClient.getInstance().player) {
            return original;
        }

        Pair<Float, Float> pitch = Client.INSTANCE.getRotationManager().rotationPitch;
        float[] rotations = Client.INSTANCE.getRotationManager().getCurrentRotation();

        Client.INSTANCE.getRotationManager().rotationPitch = new Pair<>(pitch.getRight(), rotations[1]);

        return Client.INSTANCE.getRotationManager().rotating ? Client.INSTANCE.getRotationManager().yaw : original;
    }

    @Inject(method = "travel", at = @At(value = "HEAD"), cancellable = true)
    private void hookTravelHead(Vec3d movementInput, CallbackInfo ci) {
        EventTravel eventTravel = new EventTravel();
        Client.INSTANCE.getEventManager().post(eventTravel);
        if (eventTravel.isCancelled()) {
            move(MovementType.SELF, getVelocity());
            ci.cancel();
        }
    }
}
