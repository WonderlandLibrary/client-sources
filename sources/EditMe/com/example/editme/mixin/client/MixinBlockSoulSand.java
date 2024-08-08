package com.example.editme.mixin.client;

import com.example.editme.util.module.ModuleManager;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {BlockSoulSand.class},
   priority = Integer.MAX_VALUE
)
public class MixinBlockSoulSand {
   @Inject(
      method = {"onEntityCollision"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4, CallbackInfo var5) {
      if (ModuleManager.isModuleEnabled("NoSlowDown")) {
         var5.cancel();
      }

   }
}
