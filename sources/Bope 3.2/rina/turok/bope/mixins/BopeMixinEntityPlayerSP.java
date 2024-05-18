package rina.turok.bope.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.events.BopeEventMove;

@Mixin({EntityPlayerSP.class})
public abstract class BopeMixinEntityPlayerSP extends Entity {
   private double motion_x;
   private double motion_y;
   private double motion_z;

   public BopeMixinEntityPlayerSP(World world) {
      super(world);
   }

   @Inject(
      method = "move(Lnet/minecraft/entity/MoverType;DDD)V",
      at = {@At("HEAD")}
   )
   private void move(MoverType type, double x, double y, double z, CallbackInfo info) {
      BopeEventMove event = new BopeEventMove(x, y, z);
      Bope.ZERO_ALPINE_EVENT_BUS.post(event);
   }
}
