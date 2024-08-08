package com.example.editme.mixin.client;

import com.example.editme.modules.render.Chams;
import com.example.editme.util.module.ModuleManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {RenderLiving.class},
   priority = Integer.MAX_VALUE
)
public class MixinRenderLiving {
   @Inject(
      method = {"doRender"},
      at = {@At("HEAD")}
   )
   private void injectChamsPre(EntityLiving var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
      if (ModuleManager.isModuleEnabled("Chams") && Chams.renderChams(var1)) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1000000.0F);
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("RETURN")}
   )
   private void injectChamsPost(EntityLiving var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
      if (ModuleManager.isModuleEnabled("Chams") && Chams.renderChams(var1)) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }
}
