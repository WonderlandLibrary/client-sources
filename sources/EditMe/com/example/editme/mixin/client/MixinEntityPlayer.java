package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.EntityPlayerTravel;
import com.example.editme.util.module.ModuleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {EntityPlayer.class},
   priority = Integer.MAX_VALUE
)
public abstract class MixinEntityPlayer extends EntityLivingBase {
   public MixinEntityPlayer(World var1) {
      super(var1);
   }

   @Inject(
      method = {"travel"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void travel(float var1, float var2, float var3, CallbackInfo var4) {
      EntityPlayerTravel var5 = new EntityPlayerTravel();
      EditmeMod.EVENT_BUS.post(var5);
      if (var5.isCancelled()) {
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         var4.cancel();
      }

   }

   @Inject(
      method = {"isPushedByWater"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isPushedByWater(CallbackInfoReturnable var1) {
      if (ModuleManager.isModuleEnabled("Phase")) {
         var1.setReturnValue(false);
      }

   }

   @Inject(
      method = {"applyEntityCollision"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void applyEntityCollision(Entity var1, CallbackInfo var2) {
      if (ModuleManager.isModuleEnabled("Phase")) {
         var2.cancel();
      }

   }
}
