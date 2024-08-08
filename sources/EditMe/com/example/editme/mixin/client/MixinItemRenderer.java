package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.EventRenderUpdateEquippedItem;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
public class MixinItemRenderer {
   @Inject(
      method = {"updateEquippedItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void updateEquippedItem(CallbackInfo var1) {
      EventRenderUpdateEquippedItem var2 = new EventRenderUpdateEquippedItem();
      EditmeMod.EVENT_BUS.post(var2);
      if (var2.isCancelled()) {
         var1.cancel();
      }

   }
}
