package com.example.editme.mixin.client;

import com.example.editme.util.module.ModuleManager;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {VisGraph.class},
   priority = Integer.MAX_VALUE
)
public class MixinVisGraph {
   @Inject(
      method = {"setOpaqueCube"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void setOpaqueCube(BlockPos var1, CallbackInfo var2) {
      if (ModuleManager.isModuleEnabled("Phase")) {
         var2.cancel();
      }

   }
}
