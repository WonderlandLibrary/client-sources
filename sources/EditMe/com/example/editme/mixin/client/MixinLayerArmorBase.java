package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.RenderArmorEvent;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {LayerArmorBase.class},
   priority = Integer.MAX_VALUE
)
public class MixinLayerArmorBase {
   @Inject(
      method = {"renderArmorLayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderArmorLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, EntityEquipmentSlot var9, CallbackInfo var10) {
      RenderArmorEvent var11 = new RenderArmorEvent(var1);
      EditmeMod.EVENT_BUS.post(var11);
      if (var11.isCancelled()) {
         var10.cancel();
      }

   }
}
