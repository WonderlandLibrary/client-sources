package com.example.editme.mixin.client;

import com.example.editme.modules.misc.NoEntityTrace;
import com.example.editme.modules.render.AntiFog;
import com.example.editme.modules.render.Brightness;
import com.example.editme.modules.render.NoHurtCam;
import com.example.editme.util.module.ModuleManager;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {EntityRenderer.class},
   priority = Integer.MAX_VALUE
)
public class MixinEntityRenderer {
   private boolean nightVision = false;

   @Redirect(
      method = {"orientCamera"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"
)
   )
   public RayTraceResult rayTraceBlocks(WorldClient var1, Vec3d var2, Vec3d var3) {
      return ModuleManager.isModuleEnabled("CameraClip") ? null : var1.func_72933_a(var2, var3);
   }

   @Inject(
      method = {"setupFog"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void setupFog(int var1, float var2, CallbackInfo var3) {
      if (AntiFog.enabled()) {
         var3.cancel();
      }

   }

   @Inject(
      method = {"hurtCameraEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void hurtCameraEffect(float var1, CallbackInfo var2) {
      if (NoHurtCam.shouldDisable()) {
         var2.cancel();
      }

   }

   @Redirect(
      method = {"updateLightmap"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;isPotionActive(Lnet/minecraft/potion/Potion;)Z"
)
   )
   public boolean isPotionActive(EntityPlayerSP var1, Potion var2) {
      return (this.nightVision = Brightness.shouldBeActive()) || var1.func_70644_a(var2);
   }

   @Redirect(
      method = {"updateLightmap"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/EntityRenderer;getNightVisionBrightness(Lnet/minecraft/entity/EntityLivingBase;F)F"
)
   )
   public float getNightVisionBrightnessMixin(EntityRenderer var1, EntityLivingBase var2, float var3) {
      return this.nightVision ? Brightness.getCurrentBrightness() : var1.func_180438_a(var2, var3);
   }

   @Redirect(
      method = {"getMouseOver"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"
)
   )
   public List getEntitiesInAABBexcluding(WorldClient var1, Entity var2, AxisAlignedBB var3, Predicate var4) {
      return (List)(NoEntityTrace.shouldBlock() ? new ArrayList() : var1.func_175674_a(var2, var3, var4));
   }
}
