package net.silentclient.client.mixin.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({S14PacketEntity.class, S19PacketEntityHeadLook.class, S19PacketEntityStatus.class})
public class EntityPacketsMixin {
    @Inject(
            method = {"getEntity"},
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void silent$addNullCheck(World worldIn, CallbackInfoReturnable<Entity> cir) {
        if (worldIn == null) {
            cir.setReturnValue(null);
        }
    }
}
