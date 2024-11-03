package dev.stephen.nexus.mixin.game;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.modules.render.Animations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class MixinItem {

    @ModifyReturnValue(method = "getMaxUseTime", at = @At("RETURN"))
    private int getMaxUseTimeInject(int original) {
        if (((Object) this) instanceof SwordItem && Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled()) {
            return 72000;
        }
        return original;
    }

    @ModifyExpressionValue(method = "raycast", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getRotationVector(FF)Lnet/minecraft/util/math/Vec3d;"))
    private static Vec3d raycastInject(Vec3d original, World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
        float[] rotation = Client.INSTANCE.getRotationManager().getCurrentRotation();

        if (player == MinecraftClient.getInstance().player) {
            if (!Client.INSTANCE.getRotationManager().rotating) {
                return original;
            }
            return getRotationVec(rotation[0], rotation[1]);
        }

        return original;
    }

    private static Vec3d getRotationVec(float yaw, float pitch) {
        float yawCos = MathHelper.cos(-yaw * 0.017453292f);
        float yawSin = MathHelper.sin(-yaw * 0.017453292f);
        float pitchCos = MathHelper.cos(pitch * 0.017453292f);
        float pitchSin = MathHelper.sin(pitch * 0.017453292f);

        return new Vec3d(
                yawSin * pitchCos,
                -pitchSin,
                yawCos * pitchCos
        );
    }
}
