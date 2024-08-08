package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.EventRenderMap;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.world.storage.MapData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MapItemRenderer.class})
public class MixinMapItemRenderer {
   @Inject(
      method = {"renderMap"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void render(MapData var1, boolean var2, CallbackInfo var3) {
      EventRenderMap var4 = new EventRenderMap();
      EditmeMod.EVENT_BUS.post(var4);
      if (var4.isCancelled()) {
         var3.cancel();
      }

   }
}
