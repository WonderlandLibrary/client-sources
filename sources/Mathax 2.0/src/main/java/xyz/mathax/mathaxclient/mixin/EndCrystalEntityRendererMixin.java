package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Chams;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EndCrystalEntityRenderer.class)
public abstract class EndCrystalEntityRendererMixin {
    @Mutable
    @Shadow
    @Final
    private static RenderLayer END_CRYSTAL;

    @Shadow
    @Final
    private static Identifier TEXTURE;

    @Shadow
    @Final
    public ModelPart core;

    @Shadow
    @Final
    public ModelPart frame;

    @Inject(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void render(EndCrystalEntity endCrystalEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        Chams chams = Modules.get().get(Chams.class);
        END_CRYSTAL = RenderLayer.getEntityTranslucent((chams.isEnabled() && chams.crystalsSetting.get() && !chams.crystalsTextureSetting.get()) ? Chams.BLANK : TEXTURE);
    }

    @ModifyArgs(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V", ordinal = 0))
    private void modifyScale(Args args) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.crystalsSetting.get()) {
            return;
        }

        args.set(0, 2.0F * chams.crystalsScaleSetting.get().floatValue());
        args.set(1, 2.0F * chams.crystalsScaleSetting.get().floatValue());
        args.set(2, 2.0F * chams.crystalsScaleSetting.get().floatValue());
    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EndCrystalEntityRenderer;getYOffset(Lnet/minecraft/entity/decoration/EndCrystalEntity;F)F"))
    private float getYOff(EndCrystalEntity crystal, float tickDelta) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.crystalsSetting.get()) {
            return EndCrystalEntityRenderer.getYOffset(crystal, tickDelta);
        }

        float f = (float) crystal.endCrystalAge + tickDelta;
        float g = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        g = (g * g + g) * 0.4F * chams.crystalsBounceSetting.get().floatValue();

        return g - 1.4F;
    }

    @ModifyArgs(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
    private void modifySpeed(Args args) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.crystalsSetting.get()) {
            return;
        }

        args.set(0, ((float) args.get(0)) * chams.crystalsRotationSpeedSetting.get().floatValue());
    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 3))
    private void modifyCore(ModelPart modelPart, MatrixStack matrixStack, VertexConsumer vertices, int light, int overlay) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.crystalsSetting.get()) {
            core.render(matrixStack, vertices, light, overlay);
            return;
        }

        if (chams.renderCoreSetting.get()) {
            Color color = chams.crystalsCoreColorSetting.get();
            core.render(matrixStack, vertices, light, overlay, color.r / 255f, color.g / 255f, color.b / 255f, color.a / 255f);
        }
    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 1))
    private void modifyFrame1(ModelPart modelPart, MatrixStack matrixStack, VertexConsumer vertices, int light, int overlay) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.crystalsSetting.get()) {
            frame.render(matrixStack, vertices, light, overlay);
            return;
        }

        if (chams.renderFrame1Setting.get()) {
            Color color = chams.crystalsFrame1ColorSetting.get();
            frame.render(matrixStack, vertices, light, overlay, color.r / 255f, color.g / 255f, color.b / 255f, color.a / 255f);
        }
    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 2))
    private void modifyFrame2(ModelPart modelPart, MatrixStack matrixStack, VertexConsumer vertices, int light, int overlay) {
        Chams chams = Modules.get().get(Chams.class);
        if (!chams.isEnabled() || !chams.crystalsSetting.get()) {
            frame.render(matrixStack, vertices, light, overlay);
            return;
        }

        if (chams.renderFrame2Setting.get()) {
            Color color = chams.crystalsFrame2ColorSetting.get();
            frame.render(matrixStack, vertices, light, overlay, color.r / 255f, color.g / 255f, color.b / 255f, color.a / 255f);
        }
    }
}
