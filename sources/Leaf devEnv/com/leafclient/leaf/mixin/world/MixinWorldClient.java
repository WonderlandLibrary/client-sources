package com.leafclient.leaf.mixin.world;


import com.leafclient.leaf.event.game.world.WorldSoundEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public abstract class MixinWorldClient {

    @Inject(
            method = "playSound(DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V",
            at = @At("HEAD"), cancellable = true
    )
    private void playSound(double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch,
                           boolean distanceDelay, CallbackInfo info) {
        if(EventManager.INSTANCE.publish(new WorldSoundEvent(soundIn, volume, pitch)).isCancelled()) {
            info.cancel();
        }
    }

}
