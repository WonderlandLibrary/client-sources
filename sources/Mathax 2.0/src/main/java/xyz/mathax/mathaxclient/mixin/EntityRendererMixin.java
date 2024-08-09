package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.mixininterface.IEntityRenderer;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Fullbright;
import xyz.mathax.mathaxclient.systems.modules.render.Nametags;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.render.postprocess.PostProcessShaders;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> implements IEntityRenderer {
    @Shadow
    public abstract Identifier getTexture(Entity entity);

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void onRenderLabel(T entity, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (PostProcessShaders.rendering) {
            info.cancel();
        }

        if (Modules.get().get(NoRender.class).noNametags()) {
            info.cancel();
        }

        if (!(entity instanceof PlayerEntity)) {
            return;
        }

        if (Modules.get().get(Nametags.class).playerNametags() && !(EntityUtils.getGameMode((PlayerEntity) entity) == null && Modules.get().get(Nametags.class).excludeBots())) {
            info.cancel();
        }
    }

    @Inject(method = "shouldRender", at = @At("HEAD"))
    private void shouldRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> infoReturnable) {
        NoRender noRender = Modules.get().get(NoRender.class);
        if (noRender.isEnabled()) {
            if (noRender.noEntity(entity)) {
                infoReturnable.cancel();
            }

            if (noRender.noFallingBlocks() && entity instanceof FallingBlockEntity) {
                infoReturnable.cancel();
            }
        }
    }

    @Inject(method = "getSkyLight", at = @At("RETURN"), cancellable = true)
    private void onGetSkyLight(CallbackInfoReturnable<Integer> infoReturnable) {
        infoReturnable.setReturnValue(Math.max(Modules.get().get(Fullbright.class).getLuminance(), infoReturnable.getReturnValueI()));
    }

    @Override
    public Identifier getTextureInterface(Entity entity) {
        return getTexture(entity);
    }
}
