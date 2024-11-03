package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.silentclient.client.mods.render.AnimationsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerArmorBase.class)
public class LayerArmorBaseMixin {
    @Inject(method = "shouldCombineTextures", at = @At("HEAD"), cancellable = true)
    private void shouldCombineTextures(CallbackInfoReturnable<Boolean> cir) {
        if (AnimationsMod.getSettingBoolean("Red Armor")) cir.setReturnValue(true);
    }
}
