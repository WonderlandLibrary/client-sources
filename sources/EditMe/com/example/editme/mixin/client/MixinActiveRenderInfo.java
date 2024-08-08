package com.example.editme.mixin.client;

import com.example.editme.util.render.RenderUtil;
import net.minecraft.client.renderer.ActiveRenderInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ActiveRenderInfo.class})
public class MixinActiveRenderInfo {
   @Inject(
      method = {"updateRenderInfo"},
      at = {@At("RETURN")}
   )
   private static void updateRenderInfo(CallbackInfo var0) {
      RenderUtil.updateModelViewProjectionMatrix();
   }
}
