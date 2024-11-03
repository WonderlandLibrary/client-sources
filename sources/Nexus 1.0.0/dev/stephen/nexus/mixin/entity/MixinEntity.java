package dev.stephen.nexus.mixin.entity;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.player.EventYawMoveFix;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    public static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
        return null;
    }

    @Inject(method = "setOnGround(ZLnet/minecraft/util/math/Vec3d;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;onGround:Z", ordinal = 0, shift = At.Shift.AFTER))
    private void setOnGroundInject(boolean onGround, Vec3d movement, CallbackInfo ci) {
        if ((Object) this == MinecraftClient.getInstance().player) {
            PlayerUtil.updateTicks(onGround);
        }
    }

    @Redirect(method = "updateVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;movementInputToVelocity(Lnet/minecraft/util/math/Vec3d;FF)Lnet/minecraft/util/math/Vec3d;"))
    public Vec3d updateVelocityInject(Vec3d movementInput, float speed, float yaw) {
        if ((Object) this == MinecraftClient.getInstance().player) {
            EventYawMoveFix eventYawMoveFix = new EventYawMoveFix(yaw);
            Client.INSTANCE.getEventManager().post(eventYawMoveFix);
            yaw = eventYawMoveFix.getYaw();
        }
        return movementInputToVelocity(movementInput, speed, yaw);
    }
}
