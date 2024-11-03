package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.entity.item.EntityArmorStand;
import net.silentclient.client.Client;
import net.silentclient.client.mods.render.NametagsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandRenderer.class)
public abstract class ArmorStandRendererMixin {
    @Inject(method = "canRenderName(Lnet/minecraft/entity/item/EntityArmorStand;)Z", at = @At("HEAD"), cancellable = true)
    public void nametagRenderer(EntityArmorStand entity, CallbackInfoReturnable<Boolean> cir) {
        if (!Minecraft.isGuiEnabled() && (!Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() || !Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Show in F1").getValBoolean())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
