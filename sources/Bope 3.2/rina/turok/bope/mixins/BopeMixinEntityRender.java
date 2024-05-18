package rina.turok.bope.mixins;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;

@Mixin({EntityRenderer.class})
public class BopeMixinEntityRender {
   @Redirect(
      method = "orientCamera",
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"
)
   )
   public RayTraceResult orientCamera(WorldClient world, Vec3d start, Vec3d end) {
      return Bope.get_module_manager().get_module_with_tag("FreeCameraOrient").is_active() ? null : world.rayTraceBlocks(start, end);
   }

   @Redirect(
      method = "getMouseOver",
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"
)
   )
   private List getMouseOver(WorldClient world_client, Entity entity, AxisAlignedBB bouding_box, Predicate predicate) {
      BopeModule module_requested = Bope.get_module_manager().get_module_with_tag("NoEntityTrace");
      return (List)(module_requested.is_active() && module_requested.value_boolean_0() ? new ArrayList() : world_client.getEntitiesInAABBexcluding(entity, bouding_box, predicate));
   }

   @Inject(
      method = "hurtCameraEffect",
      at = @At("HEAD"),
      cancellable = true
   )
   private void hurtCameraEffect(float ticks, CallbackInfo info) {
      if (Bope.get_module_manager().get_module_with_tag("NoHurtCam").is_active()) {
         info.cancel();
      }

   }
}
