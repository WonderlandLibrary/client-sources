package com.cout970.fira.coremod.mixin;

import com.cout970.fira.modules.CrystalPvP;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(RenderEnderCrystal.class)
public class MixinRenderEnderCrystal {

    @Inject(
        method = "doRender",
        at = @At("HEAD"),
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    public void doRender(EntityEnderCrystal entity, double x, double y, double z, float arg4, float arg5, CallbackInfo ci) {
        boolean res = CrystalPvP.INSTANCE.renderEntity(entity, (float) x, (float) y, (float) z);
        if (res) ci.cancel();
    }
}
