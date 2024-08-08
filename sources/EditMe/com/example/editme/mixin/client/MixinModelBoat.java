package com.example.editme.mixin.client;

import net.minecraft.client.model.ModelBoat;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {ModelBoat.class},
   priority = Integer.MAX_VALUE
)
public class MixinModelBoat {
   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7, CallbackInfo var8) {
   }
}
