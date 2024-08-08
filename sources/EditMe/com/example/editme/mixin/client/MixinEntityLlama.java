package com.example.editme.mixin.client;

import com.example.editme.util.module.ModuleManager;
import net.minecraft.entity.passive.EntityLlama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {EntityLlama.class},
   priority = Integer.MAX_VALUE
)
public class MixinEntityLlama {
   @Inject(
      method = {"canBeSteered"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void canBeSteered(CallbackInfoReturnable var1) {
      if (ModuleManager.isModuleEnabled("EntitySpeed")) {
         var1.setReturnValue(true);
      }

   }
}
