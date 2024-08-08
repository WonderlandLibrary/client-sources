package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.AddCollisionBoxToListEvent;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer.StateImplementation;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   value = {StateImplementation.class},
   priority = Integer.MAX_VALUE
)
public class MixinStateImplementation {
   @Shadow
   @Final
   private Block field_177239_a;

   @Redirect(
      method = {"addCollisionBoxToList"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/block/Block;addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"
)
   )
   public void addCollisionBoxToList(Block var1, IBlockState var2, World var3, BlockPos var4, AxisAlignedBB var5, List var6, @Nullable Entity var7, boolean var8) {
      AddCollisionBoxToListEvent var9 = new AddCollisionBoxToListEvent(var1, var2, var3, var4, var5, var6, var7, var8);
      EditmeMod.EVENT_BUS.post(var9);
      if (!var9.isCancelled()) {
         this.field_177239_a.func_185477_a(var2, var3, var4, var5, var6, var7, var8);
      }

   }
}
