package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.EntityEvent;
import com.example.editme.modules.movement.SafeWalk;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   value = {Entity.class},
   priority = Integer.MAX_VALUE
)
public class MixinEntity {
   @Redirect(
      method = {"applyEntityCollision"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"
)
   )
   public void addVelocity(Entity var1, double var2, double var4, double var6) {
      EntityEvent.EntityCollision var8 = new EntityEvent.EntityCollision(var1, var2, var4, var6);
      EditmeMod.EVENT_BUS.post(var8);
      if (!var8.isCancelled()) {
         var1.field_70159_w += var2;
         var1.field_70181_x += var4;
         var1.field_70179_y += var6;
         var1.field_70160_al = true;
      }
   }

   @Redirect(
      method = {"move"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/entity/Entity;isSneaking()Z"
)
   )
   public boolean isSneaking(Entity var1) {
      return SafeWalk.shouldSafewalk() || var1.func_70093_af();
   }
}
