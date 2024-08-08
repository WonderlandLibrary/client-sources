package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.RenderEntityModelEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   value = {RenderLivingBase.class},
   priority = Integer.MAX_VALUE
)
public abstract class MixinRenderLivingBase {
   @Redirect(
      method = {"renderModel"},
      at = @At(
   target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V",
   value = "INVOKE"
)
   )
   private final void renderModel(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      RenderEntityModelEvent var9 = new RenderEntityModelEvent(var1, var2, var3, var4, var5, var6, var7, var8);
      EditmeMod.EVENT_BUS.post(var9);
      if (!var9.isCancelled()) {
         var1.func_78088_a(var2, var3, var4, var5, var6, var7, var8);
      }
   }
}
