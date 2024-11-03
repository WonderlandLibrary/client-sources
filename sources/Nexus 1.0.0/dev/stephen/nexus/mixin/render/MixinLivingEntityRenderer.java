package dev.stephen.nexus.mixin.render;

import dev.stephen.nexus.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer<T extends LivingEntity> {

    private final ThreadLocal<Pair<Float, Float>> rotationPitch = ThreadLocal.withInitial(() -> null);

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void renderInject(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        Pair<Float, Float> rotationPitch = Client.INSTANCE.getRotationManager().rotationPitch;

        this.rotationPitch.set(null);

        if (livingEntity != MinecraftClient.getInstance().player) {
            return;
        }

        this.rotationPitch.set(new Pair<>(rotationPitch.getLeft(), rotationPitch.getRight()));
    }

    @Redirect(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0))
    private float renderInject(float g, float f, float s) {
        Pair<Float, Float> rot = this.rotationPitch.get();
        if (rot != null) {
            return MathHelper.lerp(g, rot.getLeft(), rot.getRight());
        } else {
            return MathHelper.lerp(g, f, s);
        }
    }
}
