package rina.turok.bope.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rina.turok.bope.Bope;

@Mixin({RenderPlayer.class})
public abstract class BopeMixinRenderPlayer {
   @Inject(
      method = "renderEntityName",
      at = @At("HEAD"),
      cancellable = true
   )
   private void renderLivingLabel(AbstractClientPlayer entity, double x, double y, double z, String name, double distanceSq, CallbackInfo callback) {
      if (Bope.get_module_manager().get_module_with_tag("NameTag").is_active()) {
         callback.cancel();
      }

   }
}
