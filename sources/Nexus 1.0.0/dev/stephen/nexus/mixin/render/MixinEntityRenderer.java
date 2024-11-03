package dev.stephen.nexus.mixin.render;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.mixin.accesors.WorldRendererAccessor;
import dev.stephen.nexus.module.modules.combat.AntiBot;
import dev.stephen.nexus.module.modules.render.ESP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void renderLabelIfPresentHead(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
        if (Client.INSTANCE.getModuleManager().getModule(ESP.class).isEnabled() && ESP._2dESP.getValue() && ESP._2dESPNameTags.getValue()) {
            if (!(entity instanceof LivingEntity)) {
                return;
            }

            if (!(entity instanceof PlayerEntity) || entity == MinecraftClient.getInstance().player && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                return;
            }

            if (Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot((PlayerEntity) entity)) {
                return;
            }

            final Box box = entity.getBoundingBox();

            if (!((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getFrustum().isVisible(box)) {
                return;
            }

            ci.cancel();
        }
    }
}
