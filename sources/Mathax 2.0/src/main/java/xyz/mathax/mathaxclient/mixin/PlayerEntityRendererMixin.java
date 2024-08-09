package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Chams;
import xyz.mathax.mathaxclient.systems.modules.render.HandView;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @ModifyArgs(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 0))
    private void modifyRenderLayer(Args args, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve) {
        Chams chams = Modules.get().get(Chams.class);
        if (chams.isEnabled() && chams.handSetting.get()) {
            Identifier texture = chams.handTextureSetting.get() ? player.getSkinTexture() : Chams.BLANK;
            args.set(1, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture)));
        }
    }

    @Redirect(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 0))
    private void redirectRenderMain(ModelPart modelPart, MatrixStack matrixStack, VertexConsumer vertices, int light, int overlay) {
        Chams chams = Modules.get().get(Chams.class);
        if (chams.isEnabled() && chams.handSetting.get()) {
            Color color = chams.handColorSetting.get();
            modelPart.render(matrixStack, vertices, light, overlay, color.r/255f, color.g/255f, color.b/255f, color.a/255f);
        } else {
            modelPart.render(matrixStack, vertices, light, overlay);
        }
    }

    @Redirect(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 1))
    private void redirectRenderSleeve(ModelPart modelPart, MatrixStack matrixStack, VertexConsumer vertices, int light, int overlay) {
        if (Modules.get().isEnabled(HandView.class)) {
            return;
        }

        Chams chams = Modules.get().get(Chams.class);
        if (chams.isEnabled() && chams.handSetting.get()) {
            Color color = chams.handColorSetting.get();
            modelPart.render(matrixStack, vertices, light, overlay, color.r/255f, color.g/255f, color.b/255f, color.a/255f);
        } else {
            modelPart.render(matrixStack, vertices, light, overlay);
        }
    }
}
