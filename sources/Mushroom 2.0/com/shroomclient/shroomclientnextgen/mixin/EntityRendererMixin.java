package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.NameTags;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Inject(
        method = "renderLabelIfPresent",
        at = @At("HEAD"),
        cancellable = true
    )
    private void dontDrawNameTag(
        T entity,
        Text text,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        CallbackInfo ci
    ) {
        if (
            ModuleManager.isEnabled(NameTags.class) &&
            NameTags.shouldNameTag(entity)
        ) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void renderMobOwners(
        T entity,
        float yaw,
        float tickDelta,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        CallbackInfo ci
    ) {
        if (
            entity.getName() != null &&
            ModuleManager.isEnabled(NameTags.class) &&
            NameTags.shouldNameTag(entity)
        ) {
            if (NameTags.mode == NameTags.Mode.Name) {
                RenderUtil.drawNameTag(tickDelta, matrices, entity);
            } else {
                RenderUtil.drawNameTag2(tickDelta, matrices, entity);
            }
        }
    }
}
