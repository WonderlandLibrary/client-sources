package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.EditmeEvent;
import com.example.editme.events.EventPlayerMotionUpdate;
import com.example.editme.events.EventPlayerUpdate;
import com.example.editme.events.PlayerMoveEvent;
import com.example.editme.util.module.ModuleManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {EntityPlayerSP.class},
   priority = Integer.MAX_VALUE
)
public class MixinEntityPlayerSP extends AbstractClientPlayer {
   public MixinEntityPlayerSP() {
      super((World)null, (GameProfile)null);
   }

   @Redirect(
      method = {"move"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"
)
   )
   public void move(AbstractClientPlayer var1, MoverType var2, double var3, double var5, double var7) {
      PlayerMoveEvent var9 = new PlayerMoveEvent(var2, var3, var5, var7);
      EditmeMod.EVENT_BUS.post(var9);
      if (var9.isCancelled()) {
      }

      super.func_70091_d(var2, var9.getX(), var9.getY(), var9.getZ());
   }

   @Redirect(
      method = {"onLivingUpdate"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"
)
   )
   public void closeScreen(EntityPlayerSP var1) {
      if (!ModuleManager.isModuleEnabled("PortalChat")) {
         ;
      }
   }

   @Redirect(
      method = {"onLivingUpdate"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"
)
   )
   public void closeScreen(Minecraft var1, GuiScreen var2) {
      if (!ModuleManager.isModuleEnabled("PortalChat")) {
         ;
      }
   }

   @Inject(
      method = {"pushOutOfBlocks"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void pushOutOfBlocks(double var1, double var3, double var5, CallbackInfoReturnable var7) {
      if (ModuleManager.isModuleEnabled("Phase")) {
         var7.setReturnValue(false);
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void OnPreUpdateWalkingPlayer(CallbackInfo var1) {
      EventPlayerMotionUpdate var2 = new EventPlayerMotionUpdate(EditmeEvent.Era.PRE);
      EditmeMod.EVENT_BUS.post(var2);
      if (var2.isCancelled()) {
         var1.cancel();
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void OnPostUpdateWalkingPlayer(CallbackInfo var1) {
      EventPlayerMotionUpdate var2 = new EventPlayerMotionUpdate(EditmeEvent.Era.POST);
      EditmeMod.EVENT_BUS.post(var2);
      if (var2.isCancelled()) {
         var1.cancel();
      }

   }

   @Inject(
      method = {"onUpdate"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onUpdate(CallbackInfo var1) {
      EventPlayerUpdate var2 = new EventPlayerUpdate();
      EditmeMod.EVENT_BUS.post(var2);
      if (var2.isCancelled()) {
         var1.cancel();
      }

   }
}
