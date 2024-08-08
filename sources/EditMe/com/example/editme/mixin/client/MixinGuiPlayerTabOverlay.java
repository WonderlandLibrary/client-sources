package com.example.editme.mixin.client;

import com.example.editme.modules.client.ExtraTab;
import com.example.editme.modules.render.TabFriends;
import com.example.editme.util.module.ModuleManager;
import java.util.List;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {GuiPlayerTabOverlay.class},
   priority = Integer.MAX_VALUE
)
public class MixinGuiPlayerTabOverlay {
   @Redirect(
      method = {"renderPlayerlist"},
      at = @At(
   value = "INVOKE",
   target = "Ljava/util/List;subList(II)Ljava/util/List;",
   remap = false
)
   )
   public List subList(List var1, int var2, int var3) {
      return var1.subList(var2, ExtraTab.INSTANCE.isEnabled() ? Math.min((Integer)ExtraTab.INSTANCE.tabSize.getValue(), var1.size()) : var3);
   }

   @Inject(
      method = {"getPlayerName"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getPlayerName(NetworkPlayerInfo var1, CallbackInfoReturnable var2) {
      if (ModuleManager.isModuleEnabled("TabFriends")) {
         var2.cancel();
         var2.setReturnValue(TabFriends.getPlayerName(var1));
      }

   }
}
