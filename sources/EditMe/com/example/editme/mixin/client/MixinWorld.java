package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.EventWorldSetBlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {World.class},
   priority = Integer.MAX_VALUE
)
public class MixinWorld {
   @Inject(
      method = {"setBlockState"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void setBlockState(BlockPos var1, IBlockState var2, int var3, CallbackInfoReturnable var4) {
      EventWorldSetBlockState var5 = new EventWorldSetBlockState(var1, var2, var3);
      EditmeMod.EVENT_BUS.post(var5);
      if (var5.isCancelled()) {
         var4.cancel();
         var4.setReturnValue(false);
      }

   }
}
