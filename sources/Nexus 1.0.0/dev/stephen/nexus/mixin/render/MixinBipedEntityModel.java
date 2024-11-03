package dev.stephen.nexus.mixin.render;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.mixin.accesors.BipedEntityModelAccessor;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.modules.combat.KillAura;
import dev.stephen.nexus.module.modules.render.Animations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class MixinBipedEntityModel {

    @Inject(method = {"setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;animateArms(Lnet/minecraft/entity/LivingEntity;F)V", shift = At.Shift.BEFORE)})
    private void setAnglesInject(LivingEntity local_1, float f, float f2, float f3, float f4, float f5, CallbackInfo callbackInfo) {
        final Module module = Client.INSTANCE.getModuleManager().getModule(Animations.class);
        if (!module.isEnabled()) {
            return;
        }

        if ((local_1.isUsingItem() && (local_1.getMainHandStack().getItem() instanceof SwordItem || local_1.getOffHandStack().getItem() instanceof SwordItem)) || (local_1.getMainHandStack().getItem() instanceof SwordItem && local_1 == MinecraftClient.getInstance().player && Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(KillAura.class).shouldRenderFakeAnim())) {

            ModelPart rightArm = ((BipedEntityModelAccessor) this).getRightArm();
            rightArm.pitch = rightArm.pitch * 0.5f - 0.9424779f;
            rightArm.yaw = -0.5235988f;
            ((BipedEntityModelAccessor) this).setRightArm(rightArm);
        }
    }
}