package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.ClientPlayerAttackEvent;
import com.example.editme.events.EventPlayerDestroyBlock;
import com.example.editme.modules.player.TpsSync;
import com.example.editme.util.client.LagCompensator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {PlayerControllerMP.class},
   priority = Integer.MAX_VALUE
)
public class MixinPlayerControllerMP {
   @Redirect(
      method = {"onPlayerDamageBlock"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"
)
   )
   float getPlayerRelativeBlockHardness(IBlockState var1, EntityPlayer var2, World var3, BlockPos var4) {
      return var1.func_185903_a(var2, var3, var4) * (TpsSync.isSync() ? LagCompensator.INSTANCE.getTickRate() / 20.0F : 1.0F);
   }

   @Inject(
      method = {"attackEntity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void attackEntity(EntityPlayer var1, Entity var2, CallbackInfo var3) {
      if (var2 != null) {
         if (var2 instanceof EntityPlayerSP) {
            ClientPlayerAttackEvent var4 = new ClientPlayerAttackEvent(var2);
            EditmeMod.EVENT_BUS.post(var4);
            if (var4.isCancelled()) {
               var3.cancel();
            }
         }

      }
   }

   @Inject(
      method = {"onPlayerDestroyBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onPlayerDestroyBlock(BlockPos var1, CallbackInfoReturnable var2) {
      EventPlayerDestroyBlock var3 = new EventPlayerDestroyBlock(var1);
      EditmeMod.EVENT_BUS.post(var3);
      if (var3.isCancelled()) {
         var2.setReturnValue(false);
         var2.cancel();
      }

   }
}
