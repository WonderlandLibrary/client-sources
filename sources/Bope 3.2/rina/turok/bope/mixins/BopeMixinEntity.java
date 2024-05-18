package rina.turok.bope.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.events.BopeEventEntity;

@Mixin({Entity.class})
public abstract class BopeMixinEntity {
   @Redirect(
      method = "applyEntityCollision",
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"
)
   )
   public void velocity(Entity entity, double x, double y, double z) {
      BopeEventEntity.BopeEventColision event = new BopeEventEntity.BopeEventColision(entity, x, y, z);
      Bope.ZERO_ALPINE_EVENT_BUS.post(event);
      if (!event.isCancelled()) {
         entity.motionX += x;
         entity.motionY += y;
         entity.motionZ += z;
         entity.isAirBorne = true;
      }
   }
}
