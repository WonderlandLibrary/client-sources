package com.leafclient.leaf.mixin.world;

import com.leafclient.leaf.event.game.world.WorldEntityEvent;
import com.leafclient.leaf.event.game.world.WorldSoundEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class MixinWorld {

    @Inject(
            method = "playSound(Lnet/minecraft/entity/player/EntityPlayer;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void playSound(@Nullable EntityPlayer player, double x, double y, double z, SoundEvent soundIn,
                           SoundCategory category, float volume, float pitch, CallbackInfo info) {
        if(EventManager.INSTANCE.publish(new WorldSoundEvent(soundIn, volume, pitch)).isCancelled()) {
            info.cancel();
        }
    }

    @Inject(
            method = "onEntityAdded",
            at = @At("INVOKE")
    )
    private void inject$entityAdd(Entity entity, CallbackInfo info) {
        EventManager.INSTANCE.publish(new WorldEntityEvent.Spawn(entity));
    }

    @Inject(
            method = "onEntityRemoved",
            at = @At("INVOKE")
    )
    private void inject$entityRemove(Entity entity, CallbackInfo info) {
        EventManager.INSTANCE.publish(new WorldEntityEvent.Despawn(entity));
    }


}
