package com.example.editme.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {RenderGlobal.class},
   priority = Integer.MAX_VALUE
)
public class MixinRenderGlobal {
   @Shadow
   Minecraft field_72777_q;
   @Shadow
   public ChunkRenderContainer field_174996_N;

   @Inject(
      method = {"renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderBlockLayer(BlockRenderLayer var1, CallbackInfo var2) {
      var2.cancel();
   }
}
