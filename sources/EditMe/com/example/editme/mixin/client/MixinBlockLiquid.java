package com.example.editme.mixin.client;

import com.example.editme.util.module.ModuleManager;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {BlockLiquid.class},
   priority = Integer.MAX_VALUE
)
public class MixinBlockLiquid {
   @Inject(
      method = {"modifyAcceleration"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void modifyAcceleration(World var1, BlockPos var2, Entity var3, Vec3d var4, CallbackInfoReturnable var5) {
      if (ModuleManager.isModuleEnabled("Velocity")) {
         var5.setReturnValue(var4);
         var5.cancel();
      }

   }
}
