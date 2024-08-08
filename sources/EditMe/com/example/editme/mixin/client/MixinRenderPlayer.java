package com.example.editme.mixin.client;

import com.example.editme.commands.Command;
import com.example.editme.modules.render.LogoutSpots;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.render.NametagRenderer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {RenderPlayer.class},
   priority = Integer.MAX_VALUE
)
public class MixinRenderPlayer {
   @Inject(
      method = {"renderEntityName"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderLivingLabel(AbstractClientPlayer var1, double var2, double var4, double var6, String var8, double var9, CallbackInfo var11) {
      if (ModuleManager.isModuleEnabled("Nametags")) {
         var11.cancel();

         try {
            if (LogoutSpots.isGhost(var1)) {
               NametagRenderer.drawGhostNameplate(var1, var2, var4, var6, (NonNullList)LogoutSpots.armorCache.get(var1.func_110124_au()));
            }
         } catch (Exception var13) {
            Command.sendChatMessage("a");
         }
      }

   }
}
