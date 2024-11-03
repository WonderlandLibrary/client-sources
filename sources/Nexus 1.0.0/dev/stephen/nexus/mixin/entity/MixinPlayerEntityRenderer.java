package dev.stephen.nexus.mixin.entity;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.modules.render.Animations;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer extends LivingEntityRenderer {

    public MixinPlayerEntityRenderer(EntityRendererFactory.Context ctx, EntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "setModelPose", at = @At(value = "TAIL"))
    private void setModelPoseInject(AbstractClientPlayerEntity player, CallbackInfo callbackInfo) {
        final Module module = Client.INSTANCE.getModuleManager().getModule(Animations.class);
        if (!module.isEnabled()) {
            return;
        }
        if (player.getOffHandStack().getUseAction() == UseAction.BLOCK || player.getMainHandStack().getUseAction() == UseAction.BLOCK) {
            final var model = (BipedEntityModel) this.getModel();
            model.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
        }
    }
}
