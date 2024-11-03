package dev.stephen.nexus.mixin.render;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.modules.combat.KillAura;
import dev.stephen.nexus.module.modules.render.Animations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class MixinHeldItemRenderer {

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER))
    private void renderFirstPersonItemInject(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        final Animations module = Client.INSTANCE.getModuleManager().getModule(Animations.class);

        if (module.isEnabled()) {
            if (Hand.MAIN_HAND == hand && player.getMainHandStack().getItem() instanceof SwordItem) {
                matrices.translate(Animations.xOffset.getValue(), Animations.yOffset.getValue(), Animations.itemScale.getValue());
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
            }
        }
    }

    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "HEAD"), cancellable = true)
    private void renderIteminject(LivingEntity player, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        final Module module = Client.INSTANCE.getModuleManager().getModule(Animations.class);

        if (!module.isEnabled()) {
            return;
        }

        if (!(stack.getItem() instanceof ShieldItem)) {
            return;
        }

        ci.cancel();
    }

    @Redirect(method = "renderFirstPersonItem", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;",
            ordinal = 0
    ))
    private UseAction renderFirstPersonItemInject(ItemStack instance) {
        var item = instance.getItem();
        if (item instanceof SwordItem && (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() &&
                Client.INSTANCE.getModuleManager().getModule(KillAura.class).shouldRenderFakeAnim() || MinecraftClient.getInstance().options.useKey.isPressed()) && Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled()) {
            return UseAction.BLOCK;
        }

        return instance.getUseAction();
    }

    @Redirect(method = "renderFirstPersonItem", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z",
            ordinal = 1
    ))
    private boolean renderFirstPersonItemInject(AbstractClientPlayerEntity instance) {
        var item = instance.getMainHandStack().getItem();

        if (item instanceof SwordItem && (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() &&
                Client.INSTANCE.getModuleManager().getModule(KillAura.class).shouldRenderFakeAnim() || MinecraftClient.getInstance().options.useKey.isPressed()) && Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled()) {
            return true;
        }

        return instance.isUsingItem();
    }

    @Redirect(method = "renderFirstPersonItem", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getActiveHand()Lnet/minecraft/util/Hand;",
            ordinal = 1
    ))
    private Hand renderFirstPersonItemInject2(AbstractClientPlayerEntity instance) {
        var item = instance.getMainHandStack().getItem();

        if (item instanceof SwordItem && (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() &&
                Client.INSTANCE.getModuleManager().getModule(KillAura.class).shouldRenderFakeAnim() || MinecraftClient.getInstance().options.useKey.isPressed()) && Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled()) {
            return Hand.MAIN_HAND;
        }

        return instance.getActiveHand();
    }

    @Redirect(method = "renderFirstPersonItem", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getItemUseTimeLeft()I",
            ordinal = 2
    ))
    private int renderFirstPersonItemInject3(AbstractClientPlayerEntity instance) {
        var item = instance.getMainHandStack().getItem();

        if (item instanceof SwordItem && (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() &&
                Client.INSTANCE.getModuleManager().getModule(KillAura.class).shouldRenderFakeAnim() || MinecraftClient.getInstance().options.useKey.isPressed()) && Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled()) {
            return 7200;
        }

        return instance.getItemUseTimeLeft();
    }

    @ModifyArg(method = "renderFirstPersonItem", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V",
            ordinal = 4
    ), index = 2)
    private float renderFirstPersonItemInject4(float equipProgress) {
        if (Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled()) {
            return 0.0F;
        }
        return equipProgress;
    }

    @Inject(method = "renderFirstPersonItem",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", ordinal = 2, shift = At.Shift.AFTER))
    private void renderFirstPersonItemInject5(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (Client.INSTANCE.getModuleManager().getModule(Animations.class).isEnabled() && item.getItem() instanceof SwordItem) {
            final Arm arm = (hand == Hand.MAIN_HAND) ? player.getMainArm() : player.getMainArm().getOpposite();
            transform(matrices, arm, swingProgress);
        }
    }

    private void transform(MatrixStack matrices, Arm arm, float swingProgress) {
        if (Animations.mode.isMode("1.7")) {
            matrices.translate(arm == Arm.RIGHT ? -0.1f : 0.1f, 0.2f, 0.1f);
            applySwingOffsetCustom(matrices, arm, swingProgress);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-102.25f));
            matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Y : RotationAxis.NEGATIVE_Y).rotationDegrees(13.365f));
            matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Z : RotationAxis.NEGATIVE_Z).rotationDegrees(78.05f));
        }

        if (Animations.mode.isMode("Exhi")) {
            matrices.translate(arm == Arm.RIGHT ? -0.1f : 0.1f, 0.2f, 0.0f);
            float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((arm == Arm.RIGHT ? 1 : -1) * g * 10.0f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -35.0f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-102.25f));
            matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Y : RotationAxis.NEGATIVE_Y).rotationDegrees(13.365f));
            matrices.multiply((arm == Arm.RIGHT ? RotationAxis.POSITIVE_Z : RotationAxis.NEGATIVE_Z).rotationDegrees(78.05f));
        }
    }

    private void applySwingOffsetCustom(MatrixStack matrices, Arm arm, float swingProgress) {
        int armSide = (arm == Arm.RIGHT) ? 1 : -1;
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(armSide * (45.0f + f * -20.0f)));
        float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(armSide * g * -20.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -80.0f));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(armSide * -45.0f));
    }
}